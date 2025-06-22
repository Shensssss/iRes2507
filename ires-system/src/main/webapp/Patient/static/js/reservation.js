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
            <button class="checkin data-id="${appointment.id}">報到</button>
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
//按報到按鈕啟動掃碼
document.addEventListener("click", async (event) => {//執行非同步點擊操作
    if (!event.target.classList.contains("checkin")) return;//確保執行報到按鈕

    try {//啟動手機後鏡頭
        const stream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } });//確保非同步請求完成才進行下一步
        const video = Object.assign(document.createElement("video"), { srcObject: stream, autoplay: true });//建立video元素;讓video連接攝影機畫面;自動播放
        document.body.appendChild(video);//把video加到body

        const canvas = document.createElement("canvas");//建立畫布
        const ctx = canvas.getContext("2d");//取得繪圖環境讓程式處理影像資料

        (function scan() {//立即執行掃描
            ctx.drawImage(video, 0, 0, canvas.width, canvas.height);//將 video 畫面截圖到 canvas,以利讀取
            //取得畫布像素的數據;解析QRcode
            const code = jsQR(ctx.getImageData(0, 0, canvas.width, canvas.height).data, canvas.width, canvas.height);

            if (code) {
                console.log("掃描結果:", code.data);//偵測成功顯示資訊
                fetch("checkin", {
                    method: "POST",//發送post請求到checkin
                    headers: { "Content-Type": "application/json" },//請求的格式
                    body: JSON.stringify({ appointmentId: event.target.dataset.id })//轉換為JSON
                }).then(response => response.json())//解析server回應
                  .then(data => console.log("報到成功:", data));//顯示回應結果

                stream.getTracks().forEach(track => track.stop());//停止相機釋放資源
                video.remove();//移除video元素
            } else {
                requestAnimationFrame(scan);//持續掃描
            }
        })();
    } catch (error) {
        console.error("相機開啟失敗:", error);//顯示錯誤訊息
    }
});

// 事件監聽
// document.addEventListener("click", (event) => {
//     if (event.target.classList.contains("check-in")) {
//         alert("啟動手機掃碼功能");
//     }
//     if (event.target.classList.contains("cancel")) {
//         alert("取消預約");
//     }
//     if (event.target.classList.contains("modify")) {
//         alert("修改預約");
//     }
//     if (event.target.classList.contains("rebook")) {
//         alert("再次預約");
//     }
//     if (event.target.classList.contains("favorite")) {
//         alert("加入收藏");
//     }
// });

//通知數量的顯示
$(function () {
    const $count = $('#notificationCount');

    $.get('/notification')
        .done(function (res) {
            const unread = res.unreadCount ?? (localStorage.getItem('unreadNotifications') || 0);
            $count.text(unread || '').toggle(!!unread);
        })
        .fail(function () {
            console.warn('通知載入失敗');
            $count.hide();
        });

    $('#notificationBell').on('click', function () {
        if (parseInt($count.text())) {
            $.post('/clearNotification', JSON.stringify({ clearUnread: true }), null, 'json')
                .done(function () {
                    localStorage.setItem('unreadNotification', 0);
                    $count.text('').hide();
                })
                .fail(() => console.warn('通知清除失敗'));
        }

        location.href = 'notification.html';
    });
});