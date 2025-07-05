(() => {
    const doctorSelect = document.querySelector('#doctor');
    const dateInput = document.querySelector('#date');
    const scheduleSelect = document.querySelector('#schedule');
    const searchBtn = document.querySelector('#btnSearchDoctor');
    const msgBox = document.querySelector('#msg');

    // 初始化 - 載入醫師清單
    init();

    // 綁定查詢按鈕
    searchBtn.addEventListener('click', () => {
        const doctor_id = doctorSelect.value;
        const date = dateInput.value;
        const schedule = scheduleSelect.value;

        if (!doctor_id || !date || !schedule) {
            let item = "";

            if (!date) {
                item += "、「日期」";
            }
            if (!schedule) {
                item += "、「時段」";
            }
            if (!doctor_id) {
                item += "、「醫生」";
            }
            msgBox.textContent = "請填寫完整資訊，請選擇" + item.substring(1);
            return;
        }

        // 將文字時段轉為對應數字
        let time_period = 0;
        switch (schedule) {
            case 'morning':
                time_period = 1;
                break;
            case 'afternoon':
                time_period = 2;
                break;
            case 'night':
                time_period = 3;
                break;
            default:
                msgBox.textContent = "請選擇有效時段";
                return;
        }

        const payload = {
            clinic_id: 1,
            doctor_id: parseInt(doctor_id),
            date: date,
            time_period: time_period
        };

        console.log("送出 payload：", payload);

        fetch("../clinic/reservequery/result", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        })
            .then(resp => resp.json())
            .then(data => {
                console.log("查詢成功", data);
                msgBox.textContent = "查詢成功！";

                const resultTableBody = document.querySelector('#resultTable tbody');
                resultTableBody.innerHTML = '';

                if (!Array.isArray(data) || data.length === 0) {
                    resultTableBody.innerHTML = '<tr><td colspan="4">查無資料</td></tr>';
                    return;
                }
                // 顯示查詢結果
                data.forEach(row => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                        <td>${row[0]}</td>
                        <td>${row[1]}</td>
                        <td>${row[2]}</td>
                        <td>${row[3]}</td>
                    `;
                    resultTableBody.appendChild(tr);
                });
            })
            .catch(error => {
                console.error("查詢失敗", error);
                msgBox.textContent = "查詢失敗，請稍後再試";
            });
    });

    function init() {
        const clinic_account = 'berlinclinic@example.com';

        fetch("../clinic/reservequery/SearchDoctor?clinic_account=" + clinic_account)
            .then(resp => resp.json())
            .then(data => {
                console.log("載入成功：", data);
                doctorSelect.innerHTML = '<option value="">請選擇</option>';
                data.forEach(doctor => {
                    const option = document.createElement("option");
                    option.value = doctor.doctor_id;
                    option.textContent = doctor.doctor_name;
                    doctorSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("載入醫師清單失敗", error);
                msgBox.textContent = "載入醫師清單失敗，請稍後再試";
            });
    }
})();
