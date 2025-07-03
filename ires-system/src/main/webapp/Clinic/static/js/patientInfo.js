
function check(){
    let name = ($("#name").val()).trim();
    let birthday = $("#datePicker").val().trim();
    let phone = ($("#phone").val()).trim();
    if(name == ""){
        alert("姓名欄位不可空白");
        return false;
    }

    if(birthday == "" && phone == ""){
        alert("生日或手機號碼欄位至少需填入一欄");
        return false;
    }

    if(phone !== "" && !(/^09\d{8}$/).test(phone)){
        alert("手機號碼格式有誤")
        return false;
    }

    return true;
}

function getGenderText(genderValue){
    switch(genderValue){
        case 1: return "男性";
        case 2: return "女性";
    }
}

function getBloodTypeText(bloodTypeValue){
    switch(bloodTypeValue){
        case 0: return "未知";
        case 1: return "A型";
        case 2: return "B型";
        case 3: return "AB型";
        case 4: return "O型";
    }
}

function getRelationText(relationValue) {
    switch (relationValue) {
        case 1: return "配偶";
        case 2: return "父母";
        case 3: return "子女";
        case 4: return "祖父母";
        case 5: return "孫子女";
        case 6: return "外祖父母";
        case 7: return "外孫子女";
        case 8: return "曾祖父母";
        case 9: return "外曾祖父母";
        case 10: return "兄弟";
        case 11: return "姊妹";
        case 12: return "甥";
        case 13: return "姪";
        case 14: return "其他親屬";
        default: return "未知";
    }
}

function showSearchedResult(patient){
    if (!patient || !patient.name) {
        $("#nameResult").text("姓名：查無資料");
        $("#notesResult").text("查無資料");
        return;
    }

    const picSrc = patient.profilePicture ? `data:image/jpeg;base64,${patient.profilePicture}` : "";
    $("#profilePictureResult").attr("src", picSrc);
    $("#nameResult").text("姓名：" + patient.name);
    let genderTypeText = getGenderText(patient.gender);
    $("#genderResult").text("性別："+genderTypeText);
    $("#birthdayResult").text("生日：" + patient.birthday);
    let bloodTypeText = getBloodTypeText(patient.bloodType);
    $("#bloodTypeResult").text("血型："+bloodTypeText);
    $("#phoneResult").text("手機號碼：" + patient.phone);
    $("#addressResult").text("地址："+patient.address);
    $("#emailResult").text("電子郵件：" + patient.email);
    $("#emergencyNameResult").text("緊急聯絡人："+patient.emergencyName);
    let relationText = getRelationText(patient.relation);
    $("#relationResult").text("關係：" + relationText);
    $("#emergencyContentResult").text("電話："+patient.emergencyContent);
    $("#notesResult").text(patient.notes);
}
    

$("#search").on("click", function(e){
    //防止submit=預設的頁面跳轉
    e.preventDefault();
    if (check()){
        let name = ($("#name").val()).trim();
        let birthday = $("#datePicker").val().trim();
        let phone = ($("#phone").val()).trim();
        fetch(`/ires-system/clinic/searchPatient?name=${name}&birthday=${birthday}&phone=${phone}`, { method: "GET" })
            .then((res) => res.json())
            .then((result) => {
                alert(result.message);
                if (result.statusCode === 200 && result.data.length > 0) {
                    //此處暫時將同名且同生日或手機號碼者視為不存在
                    showSearchedResult(result.data[0]);
                } else{
                    showSearchedResult({});
                }
            })
    }
})



function edit(){

}

$("#edit").on("click", function(){
    edit();
})







