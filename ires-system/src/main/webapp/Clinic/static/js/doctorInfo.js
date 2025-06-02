        // 新增
        $("#addBtn").on("click", function () {
            const name = $("#searchName").val().trim();

            // 檢查是否已存在
            let duplicate = false;
            $(".card .name").each(function () {
                if ($(this).text().trim() === name && name !== "") {
                    duplicate = true;
                    return false;
                }
            });

            if (duplicate) {
                alert("此位醫師已存在，請勿重複新增！");
                return;
            }

            // 不重複後開始新增，顯示新增表單(帶入輸入的姓名)
            $("#addForm").show();
            $("#addName").val(name);
            $("#addEdu1, #addEdu2, #addEdu3").val("");
            $("#addExp1, #addExp2, #addExp3").val("");
            $("#addSpec, #addTime").val("");
        });

        // 按下取消則清空欄位並隱藏表單
        $("#cancelAddBtn").on("click", function () {
            $("#addName").val("");
            $("#addEdu1, #addEdu2, #addEdu3").val("");
            $("#addExp1, #addExp2, #addExp3").val("");
            $("#addSpec, #addTime").val("");
            $("#addForm").hide();
        });

        // 按下儲存則新增醫師資料卡片
        $("#saveAddBtn").on("click", function () {
            const name = $("#addName").val().trim();
            if (!name) {
                alert("姓名不可為空白！");
                return;
            }

            // 陣列中任一處輸入空字串時=false會被filter擋下不顯示
            const edu = [$("#addEdu1").val(), $("#addEdu2").val(), $("#addEdu3").val()].filter(Boolean);
            const exp = [$("#addExp1").val(), $("#addExp2").val(), $("#addExp3").val()].filter(Boolean);
            const spec = $("#addSpec").val().trim();
            const time = $("#addTime").val().trim();

            let eduHtml = edu.map(e => `<p><span class="label">學歷：</span><span class="edu">${e}</span></p>`).join("");
            let expHtml = exp.map(e => `<p><span class="label">經歷：</span><span class="exp">${e}</span></p>`).join("");

            const cardHtml = `
                <div class="card">
                    <button class="editBtn">編輯</button>
                    <button class="deleteBtn">刪除</button>
                    <p><span class="name">${name}</span> 醫師</p>
                    ${eduHtml}
                    ${expHtml}
                    ${spec ? `<p><span class="label">專長：</span><span class="spec">${spec}</span></p>` : ""}
                    ${time ? `<p><span class="label">看診時間：</span><span class="time">${time}</span></p>` : ""}
                </div>
            `;

            $("#container").append(cardHtml);
            $("#addForm").hide();
        });

        // 刪除卡片（需確認）
        $("#container").on("click", ".deleteBtn", function () {
            if (confirm("確定要刪除此位醫師嗎？")) {
                $(this).closest(".card").remove();
            }
        });

        // 編輯按鈕點擊
        $("#container").on("click", ".editBtn", function () {
            const card = $(this).closest(".card");
            $("#editForm").data("editTarget", card);

            const name = card.find(".name").text().trim();
            const edu = card.find(".edu").map(function () { return $(this).text(); }).get();
            const exp = card.find(".exp").map(function () { return $(this).text(); }).get();
            const spec = card.find(".spec").text().trim();
            const time = card.find(".time").text().trim();

            $("#editName").val(name);
            $("#editEdu1").val(edu[0] || "");
            $("#editEdu2").val(edu[1] || "");
            $("#editEdu3").val(edu[2] || "");
            $("#editExp1").val(exp[0] || "");
            $("#editExp2").val(exp[1] || "");
            $("#editExp3").val(exp[2] || "");
            $("#editSpec").val(spec);
            $("#editTime").val(time);

            $("#editForm").show();
        });

        // 取消編輯
        $("#cancelEditBtn").on("click", function () {
            $("#editForm").hide();
            $("#editForm").removeData("editTarget");
        });

        // 儲存編輯
        $("#saveEditBtn").on("click", function () {
            const name = $("#editName").val().trim();
            if (!name) {
                alert("姓名不可為空！");
                return;
            }

            const edu = [$("#editEdu1").val(), $("#editEdu2").val(), $("#editEdu3").val()].filter(Boolean);
            const exp = [$("#editExp1").val(), $("#editExp2").val(), $("#editExp3").val()].filter(Boolean);
            const spec = $("#editSpec").val().trim();
            const time = $("#editTime").val().trim();

            let eduHtml = edu.map(e => `<p><span class="label">學歷：</span><span class="edu">${e}</span></p>`).join("");
            let expHtml = exp.map(e => `<p><span class="label">經歷：</span><span class="exp">${e}</span></p>`).join("");

            const cardHtml = `
                <div class="card">
                    <button class="editBtn">編輯</button>
                    <button class="deleteBtn">刪除</button>
                    <p><span class="name">${name}</span> 醫師</p>
                    ${eduHtml}
                    ${expHtml}
                    ${spec ? `<p><span class="label">專長：</span><span class="spec">${spec}</span></p>` : ""}
                    ${time ? `<p><span class="label">看診時間：</span><span class="time">${time}</span></p>` : ""}
                </div>
            `;

            const target = $("#editForm").data("editTarget");
            if (target) {
                target.replaceWith(cardHtml);
            }
            $("#editForm").hide();
            $("#editForm").removeData("editTarget");
        });

        // 搜尋
        $("#searchBtn").on("click", function () {
            const keyword = $("#searchName").val().trim();
            $(".card").each(function () {
                const name = $(this).find(".name").text().trim();
                $(this).toggle(keyword === "" || name.includes(keyword));
            });
        });