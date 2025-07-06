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

// AJAX請求：clinicMajor
function fetchClinicMajor(clinicId){
    fetch(`/ires-system/clinicMajor/major?clinicId=${clinicId}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" }
    })
        .then(resp => resp.json())
        .then(majors => {
            if(majors.length === 0){
                $("#clinicMajors").text("(請按下編輯按鈕進行勾選)");
            }else{
                const clinicMajors = majors.map(major => major.majorName).join('，');
                $("#clinicMajors").text(clinicMajors);

                // 取得已勾選的majaorId陣列，有值的話傳給 renderMajorCheckboxes
                // 點擊編輯按鈕時才能取得已經勾選的major，再設法預設打勾
                const selectedMajors = majors.map(major => major.majorId);
                if(selectedMajors.length > 0){
                    renderMajorCheckboxes(selectedMajors);
                }
                
            }
        })
}

// AJAX請求：editClinicMajors
function editClinicMajors(selectedMajorIds) {
    return fetch("/ires-system/clinicMajor/edit", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selectedMajorIds)
    })
        .then(resp => resp.json())
}

// AJAX請求：showInfo
function fetchShowInfo(){
    fetch("/ires-system/clinic/clinicInfo/showInfo", {
        method: "GET",
        headers: { "Content-Type": "application/json" }
    })
        .then(resp => resp.json())
        .then(result => {
            // console.log(result);
            if (result.statusCode === 200) {
                const clinic = result.data;
                $("#basicInfo").attr("data-clinic-id", clinic.clinicId);
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

                const clinicId = $("#basicInfo").attr("data-clinic-id");
                fetchClinicMajor(clinicId);
            }
        })
}

// 網頁開啟就呼叫顯示基本資料並且預覽圖片
$(document).ready(function(){
    fetchShowInfo();

    $("#editProfilePicture").on("change", function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $("#editProfilePicturePreview").attr("src", e.target.result);
            };
            reader.readAsDataURL(file);
        }
    });
});

// 從後端取得所有major長出checkbox
function renderMajorCheckboxes(selectedMajors) {
    // 一定邀先清空否則每按一次編輯會長出一次
    $("#editClinicMajors").empty();

    fetch("/ires-system/major/list", {
        method: "GET",
        headers: { "Content-Type": "application/json" }
    })
        .then(resp => resp.json())
        .then(majors => {
            majors.forEach(major => {
                const htmlText = `
                    <div>
                        <input type="checkbox" id="major_${major.majorId}" value="${major.majorName}">
                        <label for="major_${major.majorId}">${major.majorName}</label><br>
                    </div>
                `;

                $("#editClinicMajors").append(htmlText);
                if (selectedMajors.includes(major.majorId)) {
                    $(`#major_${major.majorId}`).prop("checked", true);
                }

                // 根據 selectedMajors 陣列來預設勾選
                if (selectedMajors.includes(major.majorId)) {
                    $(`#major_${major.majorId}`).prop("checked", true);
                }

            });
        })  
}

// 一、編輯基本資訊
// 按下編輯按鈕
$("#editBasicInfoBtn").on("click",function(){
// 帶入原有資料，顯示編輯表單
    $("#editName").val($("#name").text());
    $("#editAgencyId").val($("#agencyId").text());
    $("#editProfilePicturePreview").attr("src", $("#profilePicture").attr("src")).show();
    $("#editPhone").val($("#phone").text());
    $("#editAddressCity").val($("#addressCity").text());
    $("#editAddressTown").val($("#addressTown").text());
    $("#editAddressRoad").val($("#addressRoad").text());
    $("#editWeb").val($("#web").text());
    $("#editRegistrationFee").val($("#registrationFee").text());
    $("#editMemo").val($("#memo").text());

    $('#editOverlay').addClass('show');
    $("#editBasicInfoForm").show();
})

//按下取消按鈕
$('#cancelEditBtn').on('click', function() {
    $("#editName").val("");
    // 清除檔案選擇欄位（input type="file"）
    $("#editProfilePicture").val("");
    // 清除圖片預覽
    $("#editProfilePicturePreview").attr("src", '#').hide();
    $("#editPhone").val("");
    $("#editAddressCity").val("");
    $("#editAddressTown").val("");
    $("#editAddressRoad").val("");
    $("#editWeb").val("");
    $("#editRegistrationFee").val("");
    $("#editMemo").val("");

    $('#editOverlay').removeClass('show');
    $('#editBasicInfoForm').hide();
});

// 按下儲存按鈕
$('#saveEditBtn').on('click', function() {
    const name = $("#editName").val().trim();
    const phone = $("#editPhone").val().trim();
    const city = $("#editAddressCity").val().trim();
    const town = $("#editAddressTown").val().trim();
    const road = $("#editAddressRoad").val().trim();
    const web = $("#editWeb").val().trim();
    const registrationFee= $("#editRegistrationFee").val().trim();
    const memo = $("#editMemo").val().trim();

    if(!name){
        alert("診所名稱必填！")
    }

    if(!phone){
        alert("電話號碼必填！")
    }

    if(!city || !town || !road){
        alert("地址必填！")
    }

    // 收集所有打勾的 majorId放入陣列
    const selectedMajorIds = [];

    $("#editClinicMajors input[type='checkbox']:checked").each(function () {
        const majorId = parseInt($(this).attr("id").split("_")[1]);
        selectedMajorIds.push(majorId);
    });

    if( selectedMajorIds.length === 0){
        alert("請至少勾選一個專科！")
        return;
    }


    fetch("/ires-system/clinic/clinicInfo/editBasicInfo", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            clinicName: name,
            phone: phone,
            addressCity: city,
            addressTown: town,
            addressRoad: road,
            web: web,
            registrationFee: registrationFee,
            memo: memo
            // 如果需要圖片，你要先把圖片轉成Base64或其他方式傳給後端
            // profilePicture: yourBase64String
        })
    })
        .then(resp => resp.json())
        .then(result => {
            alert(result.message);
            if (result.statusCode === 200) {
                return editClinicMajors(selectedMajorIds);
            }
        })

        .then(majorResult => {
            // 這裡是 editClinicMajors 成功後
            alert(majorResult.message);
            if (majorResult.statusCode === 200) {
                $('#editOverlay').removeClass('show');
                $('#editBasicInfoForm').hide();
                fetchShowInfo();
            }
        })
})
    

