$(document).ready(function () {
  const params = new URLSearchParams(window.location.search);
  const date = params.get("date");
  const startTime = params.get("startTime");
  const endTime = params.get("endTime");
  const majorId = params.get("majorId");
  const distance = params.get("distance");

  if (date && startTime && endTime) {
    $.ajax({
      url: "/ires-system/clinic/filter",
      type: "GET",
      data: {
        date,
        startTime,
        endTime,
        majorId,
        distance,
      },
      success: function (clinics) {
        console.log(clinics);
        renderClinicList(clinics);
      },
      error: function () {
        alert("無法取得診所列表，請稍後再試！");
      },
    });
  }
  $.ajax({
    url: "/ires-system/major/list",
    method: "GET",
    success: function (data) {
      if (Array.isArray(data)) {
        if ($("#hospital_major")) {
          const select = $("#hospital_major");
          select.empty();
          select.append('<option value="all" selected>全部</option>');
          data.forEach(function (item) {
            const option = $("<option>", {
              value: item.majorId,
              text: item.majorName,
            });
            select.append(option);
          });
        }
        if ($("#hospital_major_list")) {
          const select = $("#hospital_major_list");
          const ul = $(".custom-select-options ul");
          select.empty();
          ul.empty();
          select.append('<option value="all" selected>全部</option>');
          data.forEach(function (item) {
            const option = $("<option>", {
              value: item.majorId,
              text: item.majorName,
            });
            select.append(option);

            // const li = $("<li>", {
            //   "data-value": item.majorId,
            //   text: item.majorName,
            // });
            // ul.append(li);
          });
        }

        // ul.append('<li data-value="all" class="is-highlighted">全部</li>');
      }
    },
  });
});

function renderAddress(clinics) {
  const container = $("#address_town");
  container.empty();

  if (clinics.length === 0) {
    container.append("<p>無地區篩選條件</p>");
    return;
  }

  // 建立巢狀結構：{ city: { town: count } }
  const cityMap = {};
  clinics.forEach(function (clinic) {
    const city = clinic.addressCity || "未知縣市";
    const town = clinic.addressTown || "未知地區";
    if (!cityMap[city]) cityMap[city] = {};
    if (cityMap[city][town]) {
      cityMap[city][town]++;
    } else {
      cityMap[city][town] = 1;
    }
  });

  // 輸出縣市與地區 checkbox 結構
  Object.keys(cityMap).forEach(function (city, cityIndex) {
    const cityId = `city_${cityIndex}`;
    const cityHtml = `<h5 class="fw-bold mt-3 mb-2">${city}</h5>`;
    container.append(cityHtml);

    const towns = cityMap[city];
    Object.keys(towns).forEach(function (town, townIndex) {
      const checkboxId = `locality_${cityIndex}_${townIndex}`;
      const html = `
        <div class="form-check mb-1 ms-2">
          <input
            class="form-check-input"
            type="checkbox"
            value="${town}"
            id="${checkboxId}"
          />
          <label class="form-check-label" for="${checkboxId}">
            ${town}<span class="count ms-1">(${towns[town]})</span>
          </label>
        </div>
      `;
      container.append(html);
    });
  });
}
function renderClinicList(clinics) {
  const container = $("#clinic-list");
  container.empty();

  renderAddress(clinics); // ← 新增這行

  if (clinics.length === 0) {
    container.append("<p>沒有符合條件的診所。</p>");
    return;
  }

  clinics.forEach(function (clinic) {
    const rating =
      clinic.rating == 0 || clinic.rating == null || clinic.rating == undefined
        ? `<span>尚未評論</span>`
        : `   <span class="reviews-stats">${clinic.rating.toFixed(1)}</span>
                <span class="reviews-count">(${clinic.comments})</span>`;
    // let selectedRating = 0;
    // $("#star-rating")
    //   .off("click")
    //   .on("click", "i", function () {
    //     selectedRating = Number($(this).data("value"));
    //     $("#star-rating i").each(function (idx) {
    //       if (idx < selectedRating) {
    //         $(this).removeClass("far").addClass("fas");
    //       } else {
    //         $(this).removeClass("fas").addClass("far");
    //       }
    //     });
    //   });
    let ratingStarsHtml = "";
    for (let i = 1; i <= 5; i++) {
      const iconClass = i <= Math.round(clinic.rating) ? "fas" : "far";
      ratingStarsHtml += `<i class="${iconClass} fa-star"></i>
    `;
    }
    const html = `
      <div class="border-0 card mb-3 overflow-hidden rounded card-hover shadow card-hover-sm card-hover">
        <div class="d-sm-flex hospital-list__item">
          <a href="hospital-details.html?clinicId=${
            clinic.clinicId
          }" class="bg-dark d-block flex-shrink-0 h-list__img overflow-hidden shadow">
            <img src="${
              clinic.profilePicture
                ? `data:image/jpeg;base64,${clinic.profilePicture}`
                : "static/img/1.jpeg"
            }" alt="${clinic.clinicName}" />
          </a>
          <div class="flex-grow-1 p-4">
            <div class="align-items-center d-flex fs-13 mb-2 star-rating">
              <div class="d-flex text-warning">
               ${ratingStarsHtml}
              </div>
              <div class="ms-2 review-numbers">
               ${rating}
              </div>
            </div>
            <h5 class="fs-19 fw-bold h-title mb-1 overflow-hidden text-capitalize">
              <a class="text-dark" href="hospital-details.html">${
                clinic.clinicName
              }</a>
            </h5>
            <address class="fw-medium text-primary">
              <i class="fa-solid fa-location-dot me-2"></i>${
                clinic.addressCity
              }${clinic.addressTown}${clinic.addressRoad}
            </address>
            <div class="d-flex flex-wrap mt-3">
              <a href="#" class="border directions-link fs-13 py-1 rounded-5">
                <i class="fa-solid fa-compass me-2"></i>路線</a>
              <a href="tel:${
                clinic.phone
              }" class="border directions-link fs-13 py-1 rounded-5 ms-1">
                <i class="fa-solid fa-phone me-2"></i>${clinic.phone}</a>
              <a href="#" class="border directions-link fs-13 py-1 rounded-5 ms-1">
                <i class="fa-solid fa-calendar-check me-2"></i>預約</a>
            </div>
          </div>
        </div>
      </div>
    `;
    container.append(html);
  });
}
