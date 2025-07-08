const url = `ws://${location.host}${location.pathname.substring(0, location.pathname.lastIndexOf('/') + 1)}call`;
console.log(url);
let socket;
socket = new WebSocket(url);

socket.onopen = () => {
    console.log("WebSocket 已連線至硬體設備");
    socket.send("0");
};
socket.onerror = (e) => console.error("WebSocket 錯誤", e);
socket.onclose = () => console.warn("WebSocket 已關閉");

function getStatusClass(status) {
    switch (status) {
        case '已報到': return 'status-arrived';
        case '未報到': return 'status-not-arrived';
        case '已取消': return 'status-cancelled';
        default: return '';
    }
}

function sendNumberToDevice(number, retryCount = 0) {
    if (socket.readyState === WebSocket.OPEN) {
        socket.send(number);
        console.log("傳送叫號：", number);
    } else if (socket.readyState === WebSocket.CONNECTING && retryCount < 5) {
        console.warn("WebSocket 連線中，等待 300ms 重送");
        setTimeout(() => sendNumberToDevice(number, retryCount + 1), 300);
    } else {
        console.error("WebSocket 無法使用，readyState=", socket.readyState);
    }
}

function renderRoomData() {
    const data = localStorage.getItem("callRoom");
    if (!data) {
        document.getElementById("roomCardContainer").innerHTML = "<p class='text-danger'>⚠️ 找不到診間資料</p>";
        return;
    }

    const room = JSON.parse(data);

    // 左側卡片
    const card = document.createElement("div");
    card.className = "card shadow-sm full-height-card";
    card.innerHTML = `
    <div class="card-header bg-info text-white">
      醫師：${room.doctor}（共 ${room.patients.length} 位病人）
    </div>
    <div class="card-body p-3">
      <div class="patient-table-scroll">
        <table class="table table-bordered table-sm">
          <thead class="table-light">
            <tr><th>號碼</th><th>姓名</th><th>狀態</th></tr>
          </thead>
          <tbody>
            ${room.patients.map(p => `
              <tr data-number="${p.number}">
                <td>${p.number}</td>
                <td>${p.name}</td>
                <td><span class="${getStatusClass(p.status)}">${p.status}</span></td>
              </tr>
            `).join("")}
          </tbody>
        </table>
      </div>
    </div>
  `;
    document.getElementById("roomCardContainer").appendChild(card);

    const notArrived = room.patients.filter(p => p.status === '未報到');

    const current = notArrived[0];
    if (current) {
        document.getElementById("currentNumber").innerText = `號碼：${current.number}`;
        document.getElementById("currentName").innerText = `姓名：${current.name}`;

        const row = document.querySelector(`tr[data-number='${current.number}']`);
        if (row) row.classList.add("selected-row");
    }

    const upcoming = notArrived.slice(1, 6);
    const upcomingList = document.getElementById("upcomingList");
    upcomingList.innerHTML = upcoming.length
        ? upcoming.map(p => `<li class="list-group-item">${p.number} - ${p.name}</li>`).join("")
        : `<li class="list-group-item text-muted">無將來號碼</li>`;
}

function updateCallNumberOnServer(number) {
    const room = JSON.parse(localStorage.getItem("callRoom"));
    if (!room) return;

    fetch(`/ires-system/callNumber/init?doctorId=${room.doctorId}&timePeriod=${room.timePeriod}&date=${room.date}&number=${number}`, {
        method: 'GET'
    })
        .then(res => res.json())
        .then(data => console.log("已同步更新號碼至後端", data))
        .catch(err => console.error("後端更新號碼失敗", err));
}

function insertNumber() {
    const input = document.getElementById("insertNumber");
    const value = input.value.trim();

    if (!value || isNaN(value) || parseInt(value) <= 0) {
        alert("請輸入有效的號碼");
        return;
    }

    const number = parseInt(value);
    const formattedNumber = number.toString();
    const room = JSON.parse(localStorage.getItem("callRoom"));

    const existing = room.patients.find(p => p.number === formattedNumber);
    if (!existing) {
        alert("此號碼不存在，無法插號！");
        return;
    }

    input.value = "";
    const modal = bootstrap.Modal.getInstance(document.getElementById('insertModal'));
    modal.hide();

    highlightRow(formattedNumber);
    updateCallNumberOnServer(number);
}

function prevNumber() {
    const room = JSON.parse(localStorage.getItem("callRoom"));
    const uncalled = room.patients.filter(p => p.status === "未報到");
    const currentText = document.getElementById("currentNumber").innerText.replace("號碼：", "").trim();
    const currentIndex = uncalled.findIndex(p => p.number === currentText);
    if (currentIndex > 0) {
        const prev = uncalled[currentIndex - 1].number;
        highlightRow(prev);
        updateCallNumberOnServer(prev);
    }
}

function nextNumber() {
    const room = JSON.parse(localStorage.getItem("callRoom"));
    const uncalled = room.patients.filter(p => p.status === "未報到");
    const currentText = document.getElementById("currentNumber").innerText.replace("號碼：", "").trim();
    const currentIndex = uncalled.findIndex(p => p.number === currentText);
    if (currentIndex < uncalled.length - 1) {
        const next = uncalled[currentIndex + 1].number;
        highlightRow(next);
        updateCallNumberOnServer(next);
    }
}

function highlightRow(number) {
    const numStr = number.toString();

    document.querySelectorAll("tr[data-number]").forEach(row => {
        row.querySelectorAll("td").forEach(td => td.classList.remove("selected-row"));
    });

    const row = document.querySelector(`tr[data-number='${numStr}']`);
    if (row) {
        row.querySelectorAll("td").forEach(td => td.classList.add("selected-row"));
    } else {
        console.warn("找不到符合號碼的 row：", numStr);
    }

    const room = JSON.parse(localStorage.getItem("callRoom"));
    const current = room.patients.find(p => p.number === numStr);
    if (current) {
        document.getElementById("currentNumber").innerText = `號碼：${current.number}`;
        document.getElementById("currentName").innerText = `姓名：${current.name}`;
        sendNumberToDevice(current.number);
    }

    const remaining = room.patients
        .filter(p => p.status === "未報到" && parseInt(p.number) > parseInt(numStr))
        .slice(0, 5);

    const upcomingList = document.getElementById("upcomingList");
    upcomingList.innerHTML = remaining.length
        ? remaining.map(p => `<li class="list-group-item">${p.number} - ${p.name}</li>`).join("")
        : `<li class="list-group-item text-muted">無將來號碼</li>`;
}

// 執行初始化邏輯
window.addEventListener("DOMContentLoaded", () => {
    renderRoomData();

    const room = JSON.parse(localStorage.getItem("callRoom"));
    if (room) {
        console.log("doctorId:", room.doctorId);
        console.log("timePeriod:", room.timePeriod);
        console.log("date:", room.date);

        fetch(`/ires-system/callNumber/init?doctorId=${room.doctorId}&timePeriod=${room.timePeriod}&date=${room.date}`, {
            method: 'GET'
        })
            .then(res => res.json())
            .then(data => console.log("診間叫號初始化成功", data))
            .catch(err => console.error("診間叫號初始化失敗", err));
    }

    setTimeout(() => {
        const leftCard = document.querySelector("#roomCardContainer .card");
        const rightCol = document.querySelector(".col-md-5");
        if (leftCard && rightCol) {
            leftCard.style.height = rightCol.offsetHeight + "px";
        }
    }, 200);
});