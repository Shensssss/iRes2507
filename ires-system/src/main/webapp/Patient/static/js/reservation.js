//顯示來自appointment的資料
const appointmentsList = document.getElementById("appointmentList");
const template = document.getElementById("appointmentTemplate");
const appointmentDate = date => new Date(date).toLocaleDateString("zh-TW");
const timePeriod = {
  1: "上午時段",
  2: "下午時段",
  3: "晚上時段"
};
const status = {
  0: "未報到",
  1: "已報到",
  2: "取消報到"
};
fetch('/ires-system/reservation', {
  method: 'GET',
  credentials: 'include' 
})
  .then(response => response.json())
  .then(data => {
    data.sort((a, b) => b.appointmentDate - a.appointmentDate);
    data.forEach(appointment => {
      const item = template.content.cloneNode(true);
      item.querySelector(".clinic").textContent = appointment.clinic.clinicName;
      item.querySelector(".time").textContent = 
      `預約時間: ${appointmentDate(appointment.appointmentDate)} ${timePeriod[appointment.timePeriod]}`;
      item.querySelector(".reserveNo").textContent = `看診號碼: ${appointment.reserveNo}`;
      item.querySelector(".status").textContent = `狀態: ${status[appointment.status]}`;

      const actions = item.querySelector(".actions");
      if (appointment.status == 0) {
        actions.innerHTML = `
          <button class="cancel">取消報到</button>
          <button class="edit">修改預約</button>
          <button class="favorite">加入收藏</button>
          <button class="checkIn">報到</button>
        `;
      } else {
        actions.remove();
      }

      appointmentsList.appendChild(item);
    });
  });





