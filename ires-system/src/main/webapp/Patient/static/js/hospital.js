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
        renderClinicList(clinics);
      },
      error: function () {
        alert("無法取得診所列表，請稍後再試！");
      },
    });
  }
});

function renderClinicList(clinics) {
  const container = $("#clinic-list");
  container.empty();

  if (clinics.length === 0) {
    container.append("<p>沒有符合條件的診所。</p>");
    return;
  }

  clinics.forEach(function (clinic) {
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
                <i class="fas fa-star"></i> <i class="fas fa-star"></i>
                <i class="fas fa-star-half-alt"></i>
                <i class="far fa-star"></i> <i class="far fa-star"></i>
              </div>
              <div class="ms-2 review-numbers">
                <span class="reviews-stats">4.5</span>
                <span class="reviews-count">(115)</span>
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
