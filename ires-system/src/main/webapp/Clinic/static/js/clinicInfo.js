// 將營業時間填入表格
function renderBusinessHours(clinic) {
    const morningTime = clinic.morning;
    const afternoonTime = clinic.afternoon;
    const nightTime = clinic.night;

    const weekMorning = clinic.weekMorning?.split(',').map(d => d.trim());
    const weekAfternoon = clinic.weekAfternoon?.split(',').map(d => d.trim());
    const weekNight = clinic.weekNight?.split(',').map(d => d.trim());

    // 設定早中晚時間
    $("#morning").text(`早上 ${morningTime}`);
    $("#afternoon").text(`下午 ${afternoonTime}`);
    $("#night").text(`早上 ${nightTime}`);

    // 清除先前勾勾
    for (let i = 1; i <= 7; i++) {
        $(`#morning-${i}`).text("");
        $(`#afternoon-${i}`).text("");
        $(`#night-${i}`).text("");
    }

    // 填入打勾（✔）
    if (weekMorning) {
        weekMorning.forEach(day => {
            const td = $(`#morning-${day}`);
            if (td.length > 0) {
                td.text("✔");
            }
        });
    }

    if (weekAfternoon) {
        weekAfternoon.forEach(day => {
            const td = $(`#afternoon-${day}`);
            if (td.length > 0) {
                td.text("✔");
            }
        });
    }

    if (weekNight.length > 0) {
        weekNight.forEach(day => {
            const td = $(`#night-${day}`);
            if (td) {
                td.text("✔");
            }
        });
    }
}


//AJAX請求：showInfo
function fetchShowInfo(){
    fetch('/ires-system/clinic/clinicInfo/showInfo', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
    })
        .then(resp => resp.json())
        .then(result => {
            console.log(result);
            if (result.statusCode === 200) {
                const clinic = result.data;
                $("#name").text(clinic.clinicName);
                $("#agencyId").text(clinic.agencyId);
                $("#phone").text(clinic.phone);
                $("#addressCity").text(clinic.addressCity);
                $("#addressTown").text(clinic.addressTown);
                $("#addressRoad").text(clinic.addressRoad);
                $("#web").text(clinic.web);
                $("#registrationFee").text(clinic.registrationFee);
                $("#memo").text(clinic.memo);
                $("#profilePicture").attr("src", "data:image/png;base64,"+clinic.profilePicture);
                renderBusinessHours(clinic);
            }
        })
}

// 網頁開啟就呼叫顯示基本資料
document.addEventListener("DOMContentLoaded", function() {
    fetchShowInfo();
});