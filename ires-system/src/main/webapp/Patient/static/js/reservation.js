// 顯示來自 appointment 的資料
const appointmentsList = document.getElementById("appointmentList");
const template = document.getElementById("appointmentTemplate");
const appointmentDate = date => new Date(date).toLocaleDateString("zh-TW");

const timePeriod = {
  1: "上午時段", // 9:00
  2: "下午時段", // 14:00
  3: "晚上時段"  // 19:00
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

    const now = new Date();

    data.forEach(appointment => {
      const item = template.content.cloneNode(true);
      item.querySelector(".appointment").dataset.id = appointment.id;
      item.querySelector(".clinic").textContent = appointment.clinic.clinicName;
      item.querySelector(".time").textContent =
        `預約時間: ${appointmentDate(appointment.appointmentDate)} ${timePeriod[appointment.timePeriod]}`;
      item.querySelector(".reserveNo").textContent = `看診號碼: ${appointment.reserveNo}`;
      item.querySelector(".status").textContent = `狀態: ${status[appointment.status]}`;

      const actions = item.querySelector(".actions");
      actions.innerHTML = "";

      const appointmentTime = new Date(appointment.appointmentDate);
      appointmentTime.setHours(
        appointment.timePeriod == 1 ? 9 :
        appointment.timePeriod == 2 ? 14 :
        appointment.timePeriod == 3 ? 19 : 0,
        0, 0, 0
      );

      const isFuture = appointmentTime > now;

      if (appointment.status == 0 && isFuture) {
        actions.innerHTML = `
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