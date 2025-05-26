flatpickr("#multiDate", {
    mode: "multiple",
    dateFormat: "Y-m-d",
    locale: "zh"
  });

  const patients = Array.from({ length: 30 }, (_, i) => ({
    phone: "09" + (10000000 + i),
    name: "病患" + (i + 1)
  }));
  const doctors = ["林醫師", "張醫師", "黃醫師", "陳醫師"];
  const visitNumberCounter = { current: 1 };

  function renderPatients(page = 1, keyword = "") {
    const filtered = patients.filter(p => p.phone.includes(keyword) || p.name.includes(keyword));
    const rowsPerPage = 10;
    const totalPages = Math.ceil(filtered.length / rowsPerPage);
    const tbody = document.getElementById("patientTableBody");
    const pagination = document.getElementById("pagination");

    tbody.innerHTML = "";
    pagination.innerHTML = "";

    const start = (page - 1) * rowsPerPage;
    const end = Math.min(start + rowsPerPage, filtered.length);
    for (let i = start; i < end; i++) {
      const tr = document.createElement("tr");
      tr.innerHTML = `<td>${filtered[i].phone}</td><td>${filtered[i].name}</td>`;
      tr.ondblclick = () => {
        document.getElementById("patient").value = `${filtered[i].phone} ${filtered[i].name}`;
        document.getElementById("patientTableContainer").style.display = "none";
      };
      tbody.appendChild(tr);
    }

    for (let i = 1; i <= totalPages; i++) {
      const btn = document.createElement("button");
      btn.textContent = i;
      btn.onclick = () => renderPatients(i, document.getElementById("patientSearch").value);
      pagination.appendChild(btn);
    }
  }

  function renderDoctors(page = 1, keyword = "") {
    const filtered = doctors.filter(name => name.includes(keyword));
    const rowsPerPage = 10;
    const totalPages = Math.ceil(filtered.length / rowsPerPage);
    const tbody = document.getElementById("doctorTableBody");
    const pagination = document.getElementById("doctorPagination");

    tbody.innerHTML = "";
    pagination.innerHTML = "";

    const start = (page - 1) * rowsPerPage;
    const end = Math.min(start + rowsPerPage, filtered.length);
    for (let i = start; i < end; i++) {
      const tr = document.createElement("tr");
      tr.innerHTML = `<td>${filtered[i]}</td>`;
      tr.ondblclick = () => {
        document.getElementById("doctorInput").value = filtered[i];
        document.getElementById("doctorTableContainer").style.display = "none";
      };
      tbody.appendChild(tr);
    }

    for (let i = 1; i <= totalPages; i++) {
      const btn = document.createElement("button");
      btn.textContent = i;
      btn.onclick = () => renderDoctors(i, document.getElementById("doctorSearch").value);
      pagination.appendChild(btn);
    }
  }

  function registerEventHandlers() {
    document.getElementById("patient").addEventListener("click", () => {
      document.getElementById("patientTableContainer").style.display = "block";
      renderPatients();
    });

    document.getElementById("doctorInput").addEventListener("click", () => {
      document.getElementById("doctorTableContainer").style.display = "block";
      renderDoctors();
    });

    document.getElementById("patientSearch").addEventListener("input", () => {
      renderPatients(1, document.getElementById("patientSearch").value);
    });

    document.getElementById("doctorSearch").addEventListener("input", () => {
      renderDoctors(1, document.getElementById("doctorSearch").value);
    });

    document.addEventListener("mousedown", e => {
      const inside =
        document.getElementById("patient").contains(e.target) ||
        document.getElementById("patientTableContainer").contains(e.target) ||
        document.getElementById("doctorInput").contains(e.target) ||
        document.getElementById("doctorTableContainer").contains(e.target);
      if (!inside) {
        document.getElementById("patientTableContainer").style.display = "none";
        document.getElementById("doctorTableContainer").style.display = "none";
      }
    });

    document.getElementById("btnReserve").addEventListener("click", () => {
      const dates = document.getElementById("multiDate").value.split(", ");
      const timeslot = document.getElementById("timeslot").value;
      const doctor = document.getElementById("doctorInput").value;
      const tbody = document.getElementById("appointmentList");
      const now = new Date().toISOString().replace("T", " ").substring(0, 19);

      dates.forEach(date => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td><span class="view">${date}</span><input class="edit" type="date" value="${date}" style="display:none"></td>
          <td><span class="view">${timeslot}</span>
              <select class="edit" style="display:none">
                <option ${timeslot === '早上' ? 'selected' : ''}>早上</option>
                <option ${timeslot === '下午' ? 'selected' : ''}>下午</option>
                <option ${timeslot === '晚上' ? 'selected' : ''}>晚上</option>
              </select>
          </td>
          <td><span class="view">${doctor}</span><input class="edit" type="text" value="${doctor}" style="display:none"></td>
          <td>${visitNumberCounter.current++}</td>
          <td>未報到</td>
          <td>${now}</td>
          <td class="modified"></td>
          <td>
            <button class="editBtn">修改</button>
            <button class="saveBtn" disabled>儲存</button>
            <button class="cancelBtn" disabled>取消</button>
            <button class="deleteBtn">刪除</button>
          </td>
        `;
        tbody.appendChild(tr);
      });
    });

    
    document.getElementById("appointmentList").addEventListener("click", (e) => {
      const tr = e.target.closest("tr");
      if (!tr) return;

      const editBtn = tr.querySelector(".editBtn");
      const saveBtn = tr.querySelector(".saveBtn");
      const cancelBtn = tr.querySelector(".cancelBtn");
      const deleteBtn = tr.querySelector(".deleteBtn");

      if (e.target.classList.contains("editBtn")) {
        tr.querySelectorAll(".view").forEach(el => el.style.display = "none");
        tr.querySelectorAll(".edit").forEach(el => el.style.display = "inline-block");
        editBtn.disabled = true;
        deleteBtn.disabled = true;
        saveBtn.disabled = false;
        cancelBtn.disabled = false;

        // 儲存原始值做取消用
        tr.dataset.originalDate = tr.querySelector('input[type="date"]').value;
        tr.dataset.originalTime = tr.querySelector('select').value;
        tr.dataset.originalDoctor = tr.querySelector('input[type="text"]').value;
      }

      if (e.target.classList.contains("saveBtn")) {
        const dateVal = tr.querySelector('input[type="date"]').value;
        const timeVal = tr.querySelector('select').value;
        const docVal = tr.querySelector('input[type="text"]').value;

        tr.querySelectorAll(".view")[0].textContent = dateVal;
        tr.querySelectorAll(".view")[1].textContent = timeVal;
        tr.querySelectorAll(".view")[2].textContent = docVal;

        tr.querySelector(".modified").textContent = new Date().toISOString().replace("T", " ").substring(0, 19);
        tr.querySelectorAll(".view").forEach(el => el.style.display = "inline");
        tr.querySelectorAll(".edit").forEach(el => el.style.display = "none");

        saveBtn.disabled = true;
        cancelBtn.disabled = true;
        editBtn.disabled = false;
        deleteBtn.disabled = false;
      }

      if (e.target.classList.contains("cancelBtn")) {
        tr.querySelector('input[type="date"]').value = tr.dataset.originalDate;
        tr.querySelector('select').value = tr.dataset.originalTime;
        tr.querySelector('input[type="text"]').value = tr.dataset.originalDoctor;

        tr.querySelectorAll(".edit").forEach(el => el.style.display = "none");
        tr.querySelectorAll(".view").forEach(el => el.style.display = "inline");

        saveBtn.disabled = true;
        cancelBtn.disabled = true;
        editBtn.disabled = false;
        deleteBtn.disabled = false;
      }

      if (e.target.classList.contains("deleteBtn")) {
        if (!deleteBtn.disabled && confirm("確定要刪除這筆預約嗎？")) {
          tr.remove();
        }
      }
    });

      const tr = e.target.closest("tr");
      if (!tr) return;
      const editBtn = tr.querySelector(".editBtn");
      const saveBtn = tr.querySelector(".saveBtn");
      const deleteBtn = tr.querySelector(".deleteBtn");

      if (e.target.classList.contains("editBtn")) {
        tr.querySelectorAll(".view").forEach(el => el.style.display = "none");
        tr.querySelectorAll(".edit").forEach(el => el.style.display = "inline-block");
        saveBtn.disabled = false;
        deleteBtn.disabled = true;
      }

      if (e.target.classList.contains("saveBtn")) {
        const dateVal = tr.querySelector('input[type="date"]').value;
        const timeVal = tr.querySelector('select').value;
        const docVal = tr.querySelector('input[type="text"]').value;

        tr.querySelectorAll(".view")[0].textContent = dateVal;
        tr.querySelectorAll(".view")[1].textContent = timeVal;
        tr.querySelectorAll(".view")[2].textContent = docVal;

        tr.querySelector(".modified").textContent = new Date().toISOString().replace("T", " ").substring(0, 19);
        tr.querySelectorAll(".view").forEach(el => el.style.display = "inline");
        tr.querySelectorAll(".edit").forEach(el => el.style.display = "none");

        saveBtn.disabled = true;
        deleteBtn.disabled = false;
      }

      if (e.target.classList.contains("deleteBtn")) {
        if (!deleteBtn.disabled && confirm("確定要刪除這筆預約嗎？")) {
          tr.remove();
        }
      }
    };

  document.addEventListener("DOMContentLoaded", registerEventHandlers);