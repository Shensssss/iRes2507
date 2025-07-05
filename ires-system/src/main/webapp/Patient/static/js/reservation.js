// 顯示來自 appointment 的資料
const appointmentsList = document.getElementById("appointmentList");
const template = document.getElementById("appointmentTemplate");
const modal = document.getElementById("editAppointment");
const form = document.getElementById("editForm");
const doctorSelect = document.getElementById("doctorSelect");
const doctorLabel = document.getElementById("doctorLabel");

const timePeriodText = { 1: "上午時段", 2: "下午時段", 3: "晚上時段" };
const checkInStatus = { 0: "未報到", 1: "已報到", 2: "取消報到" };

const formatDate = date => new Date(date).toLocaleDateString("zh-TW");
const getCutoffHour = period => ({ 1: 12, 2: 18, 3: 22 }[period] || 0);
const showError = (title, err) => {
  console.error(`${title}：`, err);
  alert(`${title}，請稍後再試`);
};
const getAppointmentElementById = id =>
  document.querySelector(`.appointment[data-id="${id}"]`);

// 載入可預約醫師（可用於後續互動）
function loadDoctors(clinicId, date, timePeriod, selectedDoctorId = null) {
  fetch(`/ires-system/editAppointment/available?clinicId=${clinicId}&date=${date}&timePeriod=${timePeriod}`)
    .then(r => r.json())
    .then(res => {
      doctorSelect.innerHTML = "";
      (Array.isArray(res.data) ? res.data : []).forEach(doc => {
        const opt = document.createElement("option");
        opt.value = doc.doctorId;
        opt.textContent = doc.doctorName;
        if (doc.doctorId === selectedDoctorId) opt.selected = true;
        doctorSelect.appendChild(opt);
      });
    })
    .catch(err => showError("載入醫師清單失敗", err));
}

// 載入預約資料清單
fetch('/ires-system/reservation', { method: 'GET', credentials: 'include' })
  .then(res => res.json())
  .then(data => {
    data.sort((a, b) => b.appointmentDate - a.appointmentDate);
    const now = new Date();

    data.forEach(appt => {
      if (!appt.appointmentId) return;

      const item = template.content.cloneNode(true);
      const el = item.querySelector(".appointment");
      el.dataset.id = appt.appointmentId;

      item.querySelector(".clinic").textContent = appt.clinic.clinicName;
      item.querySelector(".time").textContent =
        `預約時間: ${formatDate(appt.appointmentDate)} ${timePeriodText[appt.timePeriod]}`;
      item.querySelector(".reserveNo").textContent = `看診號碼: ${appt.reserveNo}`;
      item.querySelector(".status").textContent = `狀態: ${checkInStatus[appt.status]}`;

      const actions = item.querySelector(".actions");
      actions.innerHTML = "";

      const futureTime = new Date(appt.appointmentDate);
      futureTime.setHours(getCutoffHour(appt.timePeriod), 0, 0, 0);

      if (appt.status === 0 && now < futureTime) {
        ["修改預約", "加入收藏", "報到"].forEach((text, i) => {
          const btn = document.createElement("button");
          btn.className = ["edit", "favorite", "checkIn"][i];
          btn.textContent = text;
          if (i === 0) btn.dataset.id = appt.appointmentId;
          actions.appendChild(btn);
        });
      } else {
        actions.remove();
      }

      appointmentsList.appendChild(item);
    });
  });

// 開啟/關閉彈窗 + 載入預約資訊
document.addEventListener("click", e => {
  const editBtn = e.target.closest(".edit");
  if (editBtn) {
    const id = editBtn.dataset?.id?.trim();
    if (!id || id === "undefined") {
      alert("找不到預約 ID！");
      return;
    }

    form.dataset.id = id;

    fetch(`/ires-system/editAppointment/${id}`)
      .then(res => res.ok ? res.json() : res.text().then(t => { throw new Error(t); }))
      .then(d => {
        if (!d?.appointmentDate) return alert("預約資料格式錯誤");

        form.date.value = new Date(d.appointmentDate).toISOString().split("T")[0];
        form.timePeriod.value = d.timePeriod;

        // 使用從後端傳來的 doctorList 更新下拉選單
        doctorSelect.innerHTML = "";
        (Array.isArray(d.doctorList) ? d.doctorList : []).forEach(doc => {
          const opt = document.createElement("option");
          opt.value = doc.doctorId;
          opt.textContent = doc.doctorName;
          if (doc.doctorId === d.doctorId) opt.selected = true;
          doctorSelect.appendChild(opt);
        });

        if (doctorLabel) doctorLabel.textContent = d.doctorName;
        modal.hidden = false;
      })
      .catch(err => showError("載入預約資料失敗", err));
  }

  if (e.target.classList.contains("closeBtn")) modal.hidden = true;
});

// 表單送出 - 更新預約
form.addEventListener("submit", e => {
  e.preventDefault();

  const body = JSON.stringify({
    appointmentId: form.dataset.id,
    appointmentDate: form.date.value,
    timePeriod: form.timePeriod.value,
    doctorId: doctorSelect.value
  });

  fetch("/ires-system/editAppointment/update", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body
  })
    .then(res => res.json())
    .then(d => {
      alert(d.message || "更新成功");
      modal.hidden = true;

      const id = form.dataset.id;
      const apptEl = getAppointmentElementById(id);
      if (apptEl) {
        const period = (timePeriodText[form.timePeriod.value] || "").replace("時段", "");
        const doctor = doctorSelect.options[doctorSelect.selectedIndex]?.text || "";
        apptEl.querySelector(".time").textContent = `預約時間: ${form.date.value} ${period} ${doctor}`;
      }
    })
    .catch(err => showError("更新失敗", err));
});