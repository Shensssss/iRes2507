function check(){
    let name = ($("#name").val()).trim();
    // console.log(name);
    let birthday = $("#datePicker").val();
    let phoneNumber = ($("#phoneNumber").val()).trim();

    if(name == ""){
        alert("姓名欄位不可空白");
        return;
    }

    if(birthday == "" && phoneNumber == ""){
        alert("生日或手機號碼欄位至少需填入一欄");
        return;
    }

    if(phoneNumber !== "" && !(/^09\d{8}$/).test(phoneNumber)){
        alert("手機號碼格式有誤")
        return;
    }
    
    console.log("驗證結束");
}

function edit(){
    // if($("#notes").attr("data-edit") == undefined){
    //     $("#notes").attr("data-edit" ,true);
    //     $("#notes").toggleClass("-none");
    //     $(".detailUpdate").toggleClass("-none");
    // }else{
    //     let updateNotes = $("")
    // }
}

$("#edit").on("click", function(){
    edit();
})



$("#search").on("click", function(e){
    e.preventDefault(); //防止頁面跳轉
    check();
    console.log("aa");

    
//     fetch("/searchPatient", {
//             method: "POST",
//             headers: {
//                 "Content-type" : "application/json"
//             },
//             body: JSON.stringify({
//                     name:
//                     birthday:
//                     phoneNumber:
//                     email:
//                     notes:
//                     *所有過去的預約
//                 })            
//         })
//     .then(response => {
//         response.json();
//     })

})



// $("#queryForm").on("click", function(e){
// })


