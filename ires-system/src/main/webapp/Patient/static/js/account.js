//圖片預覽與處理
function readImage(file, callback) {//callback處理圖片的 Base64 字串
    if (!file || !file.type.startsWith("image/")) {//確保是圖片檔
        alert("請選擇有效的圖片檔案！");
    return;
    }
    const reader = new FileReader();//建立物件
    reader.onloadend = (e) => callback?.(e.target.result);//讀取Base64字串
    reader.readAsDataURL(file);// 將file轉換成 Base64 格式
}


//圖片預覽
document.getElementById("profilePicture").addEventListener("change", function(event) {
    readImage(event.target.files[0], function (imageSrc) {//取得檔案並轉為Base64格式圖片
        const preview = document.getElementById("preview");//設定圖片預覽
        preview.src = imageSrc;//設定圖片來源
        preview.style.display = "block";//顯示圖片
    });
});

//提交異動資料
document.getElementById("infoForm").addEventListener("submit", function(event) {
    event.preventDefault(); // 阻止預設表單提交
    //取得欄位的值
    const name = document.getElementById("name").value;
    const gender = document.getElementById("gender").value;
    const birthday = document.getElementById("birthday").value;
    const phone = document.getElementById("phone").value;
    const address = document.getElementById("address").value;
    const email = document.getElementById("email").value;
    const emergencyContent = document.getElementById("emergencyContent").value;
    const emergencyName = document.getElementById("emergencyName").value;
    const relation = document.getElementById("relation").value;
    const bloodType = document.getElementById("bloodType").value;
    const notes = document.getElementById("notes").value;

    //必填欄位都要有值
    if (!name || !gender || !birthday || !email) {
        alert("請填寫所有必填欄位!");//確保必填欄位必填
        return;
    }//送出表單
    sendPatientData("", name, gender, birthday, phone, address, email, emergencyContent, emergencyName, relation, bloodType, notes);
});

// 資料傳送到後端API儲存回應
function sendPatientData(profilePicture, name, gender, birthday, phone, address, email, emergencyContent, emergencyName, relation, bloodType, notes) {
    fetch("http://localhost:8080/patient", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({//物件轉為JSON格式傳送
            profilePicture,
            name,
            gender,
            birthday,
            phone,
            address,
            email,
            emergencyContent,
            emergencyName,
            relation,
            bloodType,
            notes
        })
    })
    .then(async response => {//處理非同步API的回應
    const contentType = response.headers.get("Content-Type") || "";//取得回應類型
    const errorData = contentType.includes("application/json") //解析回應內容
        ? await response.json()//解析為JSON格式
        : await response.text();//解析為純文字格式

    if (!response.ok) {//弱狀態為40x或50x則拋出錯誤
        throw new Error(errorData.message || `API 錯誤: ${response.status}`);
    }

    return errorData;
})
    .catch(error => {//請求失敗
            console.error("API 發送錯誤：", error);
            alert(`API 請求失敗：${error.message}`);//顯示錯誤資訊
        });

}

// **取得病患資料並填入表單**
async function fetchPatients() {//非同步函式
    try {//向後端拿取patient資料(get方法)
        const response = await fetch("http://localhost:8080/patient");
        //確保API回應成功，避免解析JSON失敗
        if (!response.ok) {
            throw new Error(`API 回應錯誤: ${response.status}`);
        }
        const data = await response.json();//回應json格式內容放入data
        // 檢查 API 是否回傳空資料
        if (!Array.isArray(data) || data.length == 0) {
            throw new Error("API 回傳的病患資料為空！");
        }

// 確保所有欄位正確填入
        if (data.length > 0) {        
            document.getElementById("name").value = data[0].name;
            document.getElementById("gender").value = data[0].gender;
            document.getElementById("birthday").value = data[0].birthday;
            document.getElementById("phone").value = data[0].phone;
            document.getElementById("address").value = data[0].address || "";
            document.getElementById("email").value = data[0].email;
            document.getElementById("emergencyContent").value = data[0].emergencyContent || "";
            document.getElementById("emergencyName").value = data[0].emergencyName || "";
            document.getElementById("relation").value = data[0].relation || "";
            document.getElementById("bloodType").value = data[0].bloodType;
            document.getElementById("notes").value = data[0].notes || "";
           
            const profileImg = document.getElementById("preview"); // 確保格式是img而不是input
            if (profileImg && data[0].profilePicture) {//確保有圖片
                profileImg.src = data[0].profilePicture;//設定img的src
                profileImg.hidden = false; // 顯示圖片
            }
        }
    } catch (error) {//若沒帶出則資料顯示錯誤
        console.error("API 請求錯誤：", error);
        alert(`伺服器連線失敗，錯誤訊息：${error.message}`);
    }
}
fetchPatients();//網頁載入時自動填入patient資料

//通知數量的顯示
(async function () {//立刻執行的函式
    document.addEventListener("DOMContentLoaded", async function() {
        const notificationCount = document.getElementById("notificationCount");//取得通知數量元素
        if (!notificationCount) return;//沒有就結束函式

        try {//向後端get通知數量
            const response = await fetch("http://localhost:8080/notifications");
            if (!response.ok) throw new Error(`HTTP 錯誤：${response.status}`);//回應失敗拋出錯誤

            const data = await response.json();//將API回應解析為JSON格式存入data
            //計算未讀通知數量
            let unreadCount = data.unreadCount ?? parseInt(localStorage.getItem("unreadNotifications") || "0");
            //更新通知數量
            notificationCount.textContent = unreadCount > 0 ? unreadCount : "";//大於0就顯示數值,沒有就清空
            notificationCount.style.display = unreadCount > 0 ? "" : "none";//大於0顯示數量,沒有就隱藏數量
        } catch (error) {//API請求發生錯誤
            console.error("通知 API 請求失敗：", error);
            notificationCount.style.display = "none";//隱藏通知數量
        }
    });
})();

document.getElementById("notificationBell").addEventListener("click", async function() {
    const notificationCount = document.getElementById("notificationCount");//取得通知計數元素
    let unreadCount = parseInt(notificationCount.textContent) || 0;//解析未讀通知數量為數字格式
    
    if (unreadCount > 0) {//有未讀通知才清空
        try {
            await fetch("http://localhost:8080/clear-notification", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ clearUnread: true })//轉換為JSON格式,代表通知已讀
            });

            localStorage.setItem("unreadNotifications", 0);//頁面保持已讀
            notificationCount.textContent = "";//清空通知數量
            notificationCount.hidden = false;//隱藏數量
        } catch (error) {//顯示錯誤
            console.error("清空通知 API 失敗：", error);
        }
    }
    
    window.location.href = "notification.html"; // 點擊直接跳轉到通知頁面
});