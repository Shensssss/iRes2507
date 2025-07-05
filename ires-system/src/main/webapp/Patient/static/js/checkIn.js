//報到按鈕啟動掃碼
document.addEventListener("click", e => {
  if (!e.target.classList.contains("checkIn")) return;

  if (!navigator.mediaDevices?.getUserMedia) {
    alert("不支援攝影機");
    return;
  }

  navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } })
    .then(stream => {
      const video = document.createElement("video");
      video.srcObject = stream;
      video.setAttribute("playsinline", true);
      video.muted = true;
      video.autoplay = true;

      video.style.position = "fixed";
      video.style.top = "0";
      video.style.left = "0";
      video.style.width = "100vw";
      video.style.height = "100vh";
      video.style.zIndex = "9999";

      document.body.appendChild(video);

      video.play().catch(err => console.error("video 播放失敗", err));

      const canvas = document.createElement("canvas");
      const ctx = canvas.getContext("2d");

      video.addEventListener("loadedmetadata", () => {
        canvas.width = video.videoWidth || 640;
        canvas.height = video.videoHeight || 480;

        if (typeof jsQR !== "function") {
          alert("jsQR 未載入，無法執行掃描");
          return;
        }

        console.log("開始掃描");

        const scan = () => {
          ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
          const imgData = ctx.getImageData(0, 0, canvas.width, canvas.height);
          const code = jsQR(imgData.data, canvas.width, canvas.height);

          if (code) {
            console.log("掃描成功:", code.data);

            fetch("/ires-system/checkIn", {
              method: "POST",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify({ appointmentId: code.data })
            })
              .then(res => res.json())
              .then(() => {
                const actions = e.target.closest(".actions");
                const btnGroup = actions.querySelector(".group.checkIn");

                btnGroup.hidden = false;

                btnGroup.querySelector("button.checkIn")?.remove();

                const cancelBtn = document.createElement("button");
                cancelBtn.className = "cancel";
                cancelBtn.textContent = "取消報到";
                cancelBtn.dataset.id = e.target.dataset.id;

                btnGroup.appendChild(cancelBtn);
              });

            // 停止攝影機並移除畫面元素
            stream.getTracks().forEach(t => t.stop());
            video.remove();
          } else {
            requestAnimationFrame(scan);
          }
        };

        scan();
      });
    })
    .catch(err => {
      console.error("相機錯誤", err);
      alert("請檢查權限設定");
    });
});