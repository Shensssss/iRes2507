const appointmentsData = [
    { clinic: "OO診所", time: "2025-05-13 10:00", status: "預約成功" },
    { clinic: "XX診所", time: "2025-05-14 14:30", status: "已取消預約" },
    { clinic: "YY診所", time: "2025-05-10 09:00", status: "已完成報到" },
    { clinic: "ZZ診所", time: "2025-05-15 09:30", status: "未完成報到" }
];

const appointmentsList = document.getElementById("appointments-list");

appointmentsData.forEach(appointment => {
    const div = document.createElement("div");
    div.classList.add("appointment");
    
    let actionsHTML = "";
    
    if (appointment.status === "預約成功") {
        actionsHTML = `
            <button class="cancel">取消預約</button>
            <button class="modify">修改預約</button>
            <button class="favorite">加入收藏</button>
            <button class="check-in">報到</button>
        `;
    } else if (["已取消預約", "已完成報到", "未完成報到"].includes(appointment.status)) {
        actionsHTML = `
            <button class="rebook">再次預約</button>
            <button class="favorite">加入收藏</button>
        `;
    }

    div.innerHTML = `
        <div class="info">
            <h3>${appointment.clinic}</h3>
            <p>預約時間: ${appointment.time}</p>
            <p>狀態: ${appointment.status}</p>
        </div>
        <div class="actions">
            ${actionsHTML}
        </div>
    `;

    appointmentsList.appendChild(div);
});

// 事件監聽
document.addEventListener("click", (event) => {
    if (event.target.classList.contains("check-in")) {
        alert("啟動手機掃碼功能");
    }
    if (event.target.classList.contains("cancel")) {
        alert("取消預約");
    }
    if (event.target.classList.contains("modify")) {
        alert("修改預約");
    }
    if (event.target.classList.contains("rebook")) {
        alert("再次預約");
    }
    if (event.target.classList.contains("favorite")) {
        alert("加入收藏");
    }
});
