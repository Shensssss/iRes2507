let clinicName = "";
let clinicAddress = "";
let clinicPhone = "";
let clinicEmail = "";
let clinicMemo = "";
let doctorList = "";
let doctorSelect = "";
let majorList = "";
let commentHtml = "";
$(document).ready(function () {
  const params = new URLSearchParams(window.location.search);
  const clinicId = params.get("clinicId");
  $.ajax({
    url: `/ires-system/clinic/id/${clinicId}`,
    type: "GET",
    success: (clinic) => {
      const address =
        clinic.addressCity + clinic.addressTown + clinic.addressRoad;
      clinicName = clinic.clinicName;
      clinicAddress = address;
      clinicPhone = clinic.phone;
      clinicEmail = clinic.account;
      clinicMemo = clinic.memo;
      $.ajax({
        url: `/ires-system/clinicMajor/major?clinicId=${clinicId}`,
        type: "GET",
        success: (major) => {
          renderMajorList(major);
        },
        error: (e) => {
          alert(e);
        },
      });
      $.ajax({
        url: `/ires-system/evaluations/list?clinicId=${clinicId}`,
        type: "GET",
        success: (data) => {
          renderComment(data);

          $.ajax({
            url: "/ires-system/doctorInfo",
            type: "POST",
            success: function (doctor) {
              renderDoctorselect(doctor);
              renderDoctorList(doctor);
              renderhospitalDetails(clinic);

              const dateParam = params.get("date");
              const timeParam = params.get("time");

              if (dateParam) {
                $('input[type="date"]').val(dateParam);
              }

              if (timeParam !== null) {
                $('select[name="time"]').val(timeParam);
              }

              setTimeout(init, 300);
            },
            error: function () {
              alert("無法取得診所列表，請稍後再試！");
            },
          });
        },
        error: (e) => alert(e),
      });
    },
    error: (e) => {
      alert(e);
    },
  });
});

// google.maps.event.addDomListener(window, "load", init);

function init() {
  var mapOptions = {
    zoom: 17,
    center: new google.maps.LatLng(25.0821576, 121.5750363),
  };

  var mapElement = document.getElementById("map");

  var map = new google.maps.Map(mapElement, mapOptions);

  var marker = new google.maps.Marker({
    position: new google.maps.LatLng(25.0821576, 121.5750363),
    map: map,
    title: clinicName,
  });

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      function (position) {
        const userLat = position.coords.latitude;
        const userLng = position.coords.longitude;

        const userLocation = new google.maps.LatLng(userLat, userLng);
        const clinicLocation = new google.maps.LatLng(25.0821576, 121.5750363);

        const distanceInMeters =
          google.maps.geometry.spherical.computeDistanceBetween(
            userLocation,
            clinicLocation
          );
        const distanceInKm = (distanceInMeters / 1000).toFixed(2);

        document.getElementById(
          "distance-result"
        ).innerText = `您目前距離診所約 ${distanceInKm} 公里`;
      },
      function () {
        document.getElementById("distance-result").innerText =
          "⚠️ 無法取得您的定位資訊";
      }
    );
  }
}

function renderDoctorList(doctors) {
  if (doctors.length === 0) {
    doctorList = "<p>沒有符合條件的診所。</p>";
    return;
  }

  doctors.forEach(function (doctor) {
    doctorList += `
      <div class="col-md-6 d-flex">
                      <div class="card-body p-0">
                        <div class="bg-white d-flex p-3 p-lg-4 p-md-3 p-sm-4">
                          <div
                            class="d-block doctor-avatar flex-shrink-0 me-3 p-2 position-relative rounded-bottom rounded-circle shadow"
                          >
                            <div class="online position-absolute">
                              <span class="heartbit"></span>
                              <span class="point"></span>
                            </div>
                            <img
                              src="static/picture/011.jpg"
                              alt=""
                              class="rounded-bottom rounded-circle"
                            />
                          </div>
                          <div class="flex-grow-1">
                            <h6 class="fs-17 fw-semibold mb-2 mb-sm-1">
                              ${doctor.doctorName}
                            </h6>
                            <div
                              class="fw-semibold mb-0 mb-sm-2 small text-primary"
                            >
                              ${doctor.education}
                            </div>
                            <div class="d-none d-sm-block small"> ${doctor.memo}</div>
                            <div class="d-none d-sm-block small">
                              小兒科、腸胃科
                            </div>
                          </div>
                        </div>
                        <div
                          class="bg-white p-3 p-lg-4 p-md-3 p-sm-4 pt-0 pt-lg-0 pt-sm-0"
                        >
                          <div class="row g-3">
                            <div class="col-auto col-md-12">
                              <h6 class="fs-13 mb-1 text-muted">工作於</h6>
                              <div class="fw-bold text-black fs-15">
                                ${clinicName}
                              </div>
                            </div>

                            <div class="col-auto">
                              <h6 class="fs-13 mb-1 text-muted">經歷</h6>
                              <div class="fw-bold text-black fs-15">${doctor.experience}</div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
    `;
  });
}
function renderComment(comments) {
  if (comments.length === 0) {
    commentHtml = "<p>沒有人留言。</p>";
    return;
  }

  comments.forEach(function (comment) {
    const avatar =
      comment.patient && comment.patient.profilePicture
        ? comment.patient.profilePicture
        : "static/picture/041.jpg";

    const name =
      comment.patient && comment.patient.name
        ? comment.patient.name
        : "匿名使用者";
    const rating = parseInt(comment.rating) || 0;
    // 產生星星 HTML
    let starsHtml = "";
    for (let i = 1; i <= 5; i++) {
      starsHtml += `<i class="${i <= rating ? "fas" : "far"} fa-star"></i>`;
    }

    commentHtml += `
      <div class="mb-4 pb-4 border-bottom">
        <div class="d-flex align-items-start">
          <div class="text-center me-4" style="min-width: 80px;">
            <img src="${avatar}" class="rounded-circle mb-2 d-block" alt="頭像" width="64" height="64" style="min-width: 64px; max-width: 64px; height: 64px; object-fit: cover; flex-shrink: 0;">
            <div class="fw-semibold">${name}</div>
          </div>
          <div class="flex-grow-1">
            <div class="mb-2 text-warning">
              ${starsHtml}
            </div>
            <div class="bg-light p-3 rounded shadow-sm">
              <div class="readmore js-read-more" data-rm-words="80">
                ${comment.comment}
              </div>
            </div>
          </div>
        </div>
      </div>
    `;
  });
}

function renderDoctorselect(doctors) {
  doctors.forEach(function (doctor) {
    doctorSelect += `
      <option value="${doctor.doctor_id}">${doctor.doctorName}</option>
    `;
  });
}

function renderMajorList(majors) {
  majors.forEach(function (major) {
    majorList += `
       <option value=${major.majorId}>${major.majorName}</option>
    `;
  });
}

function renderhospitalDetails(clinic) {
  const container = $("#hospital-details-page");
  container.empty();

  if (!clinic) {
    container.append("<p>沒有符合條件的診所。</p>");
    return;
  }

  const html = `
     <div
          class="bg-img-hero position-relative profile-header py-5 text-white background-no-repeat background-size-cover"
        >
          <div class="container mt-5 mb-4 position-relative pt-5 z-index-1">
            <div class="row justify-content-center">
              <div class="col-lg-7">
                <div
                  class="doctor-info position-relative text-center text-white"
                >
                  <div
                    class="align-items-center d-flex fs-13 justify-content-center mb-2 star-rating"
                  >
                    <div class="star-rating__box">
                      <i
                        class="align-items-center d-inline-flex fa-star fas justify-content-center rounded-1 bg-warning text-dark"
                      ></i>
                      <i
                        class="align-items-center d-inline-flex fa-star fas justify-content-center rounded-1 bg-warning text-dark"
                      ></i>
                      <i
                        class="align-items-center d-inline-flex fa-star fas justify-content-center rounded-1 bg-warning text-dark"
                      ></i>
                      <i
                        class="align-items-center d-inline-flex fa-star fas justify-content-center rounded-1 bg-warning text-dark"
                      ></i>
                      <i
                        class="far fa-star align-items-center d-inline-flex justify-content-center rounded-1 bg-warning text-dark"
                      ></i>
                    </div>
                    <div class="ms-2 review-numbers">
                      <span class="reviews-stats">4.5</span>
                      <span class="reviews-count">(862)</span>
                    </div>
                  </div>
                  <h1 class="display-5 fw-semibold mb-5 text-white">
                    ${clinicName}
                  </h1>
                  <div class="fs-25 fw-semibold">
                    電話:
                    <span
                      class="bg-warning fw-bold px-3 py-1 rounded text-danger"
                      >${clinicPhone}</span
                    >
                  </div>
                  <div class="h-email text-decoration-underline">
                    ${clinicEmail}
                  </div>
                  <div class="fs-16 fw-medium l-spacing-1 mt-3 text-uppercase">
                    <i class="fa-solid fa-location-dot me-2"></i
                    >${clinicAddress}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="h-details_content py-5">
          <div class="container py-3">
            <div class="row">
              <div class="col-md-8 content pe-xxl-5">
                <div class="cs-content">
                  <div class="mb-5">
                    <h4 class="fw-semibold mb-4 text-capitalize">
                      <span class="underline position-relative text-primary"
                        >關於我們</span
                      >
                    </h4>
                    <div
                      class="card shadow border-0 text-inherit flex-fill w-100"
                    >
                      <div class="card-body p-4 p-sm-5">
                        <div class="readmore js-read-more" data-rm-words="80">
                          ${clinicMemo}
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="mb-5">
                    <h4 class="fw-semibold mb-4 text-capitalize">
                      <span class="underline position-relative text-primary"
                        >診療科別</span
                      >
                    </h4>
                    <div class="g-4 justify-content-center row">
                      <div class="col-sm-6 col-md-4 col-lg-3 col-xl-4 d-flex">
                        <a
                          href="#"
                          class="card shadow border-0 text-inherit flex-fill w-100"
                        >
                          <div class="card-body text-center">
                            <h6
                              class="speciality-title overflow-hidden mb-1 fw-bold fs-18 mb-3"
                            >
                              過敏免疫與臨床醫學科
                            </h6>
                            <img
                              src="static/picture/01.jpg"
                              alt="..."
                              height="80"
                              width="80"
                              class="mb-3"
                            />
                            <div class="mb-2 small">
                              管理過敏反應並調理免疫系統
                            </div>
                            <small class="text-primary fw-medium d-block"
                              >反覆感染、免疫力低下</small
                            >
                          </div>
                        </a>
                      </div>
                      <div class="col-sm-6 col-md-4 col-lg-3 col-xl-4 d-flex">
                        <a
                          href="#"
                          class="card shadow border-0 text-inherit flex-fill w-100"
                        >
                          <div class="card-body text-center">
                            <h6
                              class="speciality-title overflow-hidden mb-1 fw-bold fs-18 mb-3"
                            >
                              減重醫學科
                            </h6>
                            <img
                              src="static/picture/02.png"
                              alt="..."
                              height="80"
                              width="80"
                              class="mb-3"
                            />
                            <div class="mb-2 small">
                              適用於因肥胖引起之健康問題
                            </div>
                            <small class="text-primary fw-medium d-block"
                              >反覆性感染、免疫力低下</small
                            >
                          </div>
                        </a>
                      </div>
                      <div class="col-sm-6 col-md-4 col-lg-3 col-xl-4 d-flex">
                        <a
                          href="#"
                          class="card shadow border-0 text-inherit flex-fill w-100"
                        >
                          <div class="card-body text-center">
                            <h6
                              class="speciality-title overflow-hidden mb-1 fw-bold fs-18 mb-3"
                            >
                              心臟科
                            </h6>
                            <img
                              src="static/picture/03.jpg"
                              alt="..."
                              height="80"
                              width="80"
                              class="mb-3"
                            />
                            <div class="mb-2 small">
                              治療心臟與高血壓相關疾病
                            </div>
                            <small class="text-primary fw-medium d-block"
                              >反覆性感染、免疫力低下</small
                            >
                          </div>
                        </a>
                      </div>
                      <div class="col-sm-6 col-md-4 col-lg-3 col-xl-4 d-flex">
                        <a
                          href="#"
                          class="card shadow border-0 text-inherit flex-fill w-100"
                        >
                          <div class="card-body text-center">
                            <h6
                              class="speciality-title overflow-hidden mb-1 fw-bold fs-18 mb-3"
                            >
                              感冒、咳嗽與發燒
                            </h6>
                            <img
                              src="static/picture/04.jpg"
                              alt="..."
                              height="80"
                              width="80"
                              class="mb-3"
                            />
                            <div class="mb-2 small">
                              針對常見健康問題有效處理
                            </div>
                            <small class="text-primary fw-medium d-block"
                              >發燒、眼睛感染、胃痛、頭痛</small
                            >
                          </div>
                        </a>
                      </div>
                      <div class="col-sm-6 col-md-4 col-lg-3 col-xl-4 d-flex">
                        <a
                          href="#"
                          class="card shadow border-0 text-inherit flex-fill w-100"
                        >
                          <div class="card-body text-center">
                            <h6
                              class="speciality-title overflow-hidden mb-1 fw-bold fs-18 mb-3"
                            >
                              大腸直腸外科
                            </h6>
                            <img
                              src="static/picture/05.jpg"
                              alt="..."
                              height="80"
                              width="80"
                              class="mb-3"
                            />
                            <div class="mb-2 small">
                              針對直腸、肛門與大腸疾病
                            </div>
                            <small class="text-primary fw-medium d-block"
                              >發炎性腸道疾病、瘺管、腸阻塞</small
                            >
                          </div>
                        </a>
                      </div>
                      <div class="col-sm-6 col-md-4 col-lg-3 col-xl-4 d-flex">
                        <a
                          href="#"
                          class="card shadow border-0 text-inherit flex-fill w-100"
                        >
                          <div class="card-body text-center">
                            <h6
                              class="speciality-title overflow-hidden mb-1 fw-bold fs-18 mb-3"
                            >
                              諮商
                            </h6>
                            <img
                              src="static/picture/06.jpg"
                              alt="..."
                              height="80"
                              width="80"
                              class="mb-3"
                            />
                            <div class="mb-2 small">
                              情緒或心理健康困擾有效處理
                            </div>
                            <small class="text-primary fw-medium d-block"
                              >憂鬱、焦慮、心理壓力、創傷</small
                            >
                          </div>
                        </a>
                      </div>
                    </div>
                  </div>
                  <div class="mb-5">
                    <h4 class="fw-semibold mb-4 text-capitalize">
                      <span class="underline position-relative text-primary"
                        >醫生</span
                      >
                    </h4>
                    <div class="row g-4">
                      ${doctorList}
                    </div>
                  </div>
                  <div class="mb-5">
                    <h4 class="fw-semibold mb-4 text-capitalize">
                      <span class="underline position-relative text-primary"
                        >地理位置</span
                      >
                      <div class="text-center">
                        <p id="distance-result" class="mt-2 text-success"></p>
                      </div>
                    </h4>
                    <div id="map" class="border rounded-4"></div>
                  </div>
                </div>
              </div>
              <div class="col-md-4 sidebar">
                <div class="appointment-form">
                  <h4 class="fw-semibold mb-4 text-capitalize">
                    預約
                    <span class="underline position-relative text-primary"
                      >看診</span
                    >
                  </h4>
                  <ul class="nav nav-tabs mb-5" role="tablist">
                    <li class="nav-item" role="presentation">
                      <button
                        class="nav-link ms-0 active"
                        id="tab-one"
                        data-bs-toggle="tab"
                        data-bs-target="#tab-one-pane"
                        type="button"
                        role="tab"
                        aria-controls="tab-one-pane"
                        aria-selected="true"
                      >
                        初診
                      </button>
                    </li>
                    <li class="nav-item" role="presentation">
                      <button
                        class="nav-link"
                        id="tab-two"
                        data-bs-toggle="tab"
                        data-bs-target="#tab-two-pane"
                        type="button"
                        role="tab"
                        aria-controls="tab-two-pane"
                        aria-selected="false"
                      >
                        複診
                      </button>
                    </li>
                  </ul>
                  <div class="tab-content">
                    <div
                      class="tab-pane fade show active"
                      id="tab-one-pane"
                      role="tabpanel"
                      aria-labelledby="tab-one"
                      tabindex="0"
                    >
                      <form>
                        <div class="row g-4">
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required">預約日期</label>
                              <input
                                type="date"
                                class="form-control datepicker"
                              />
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                            <label class="required">預約時段</label>
                             <select name="time" class="form-select">
                                <option value="0">上午</option>
                                <option value="1">下午</option>
                                <option value="2">晚上</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label class="required">科別</label>
                              <select name="state" class="form-select">
                                <option value="none">不指定</option>
                               ${majorList}
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label class="required">醫生</label>
                              <select name="state" class="form-select">
                                <option value="none">不指定</option>
                                ${doctorSelect}
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required"> 預約類型</label>
                              <select name="state" class="form-select">
                                <option value="Live Video Call">
                                  醫師診間
                                </option>
                                <option value="Live Video Call">
                                  線上看診
                                </option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required">診所地點</label>
                              <select name="state" class="form-select">
                                <option value=${clinicName}>${clinicName}</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required">病況類別</label>
                              <select name="state" class="form-select">
                                <option value="New">近期發病</option>
                                <option value="Old">長期病症</option>
                                <option value="Report">報告查詢</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required">描述病徵</label>
                              <textarea
                                class="form-control"
                                placeholder="請填寫你的病徵"
                                rows="2"
                              ></textarea>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-check text-start">
                              <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="flexCheckDefaultOne"
                              />
                              <label
                                class="form-check-label"
                                for="flexCheckDefaultOne"
                              >
                                我同意
                                <a href="#" class="text-decoration-underline"
                                  >條款 &amp; 隱私政策</a
                                >
                              </label>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <button
                              class="btn btn-primary btn-lg w-100"
                              type="submit"
                            >
                              預約看診
                            </button>
                          </div>
                        </div>
                        <hr />
                        <div class="text-center">
                          <span
                            >已經有帳號了嗎？
                            <a href="./login.html">登入</a></span
                          >
                        </div>
                      </form>
                    </div>
                    <div
                      class="tab-pane fade"
                      id="tab-two-pane"
                      role="tabpanel"
                      aria-labelledby="tab-two"
                      tabindex="0"
                    >
                      <form>
                        <div class="row g-4">
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required">預約日期</label>
                              <input
                                type="date"
                                class="form-control datepicker"
                              />
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required">預約時段</label>
                              <select name="time" class="form-select">
                                <option value="0">上午</option>
                                <option value="1">下午</option>
                                <option value="2">晚上</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label class="required">科別</label>
                              <select name="state" class="form-select">
                                 <option value="none">不指定</option>
                               ${majorList}
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label class="required">醫生</label>
                              <select name="state" class="form-select">
                                <option value="none">不指定</option>
                                ${doctorSelect}
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required"> 預約類型</label>
                              <select name="state" class="form-select">
                                <option value="Live Video Call">
                                  醫師診間
                                </option>
                                <option value="Live Video Call">
                                  線上看診
                                </option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required">診所地點</label>
                              <select name="Department" class="form-select">
                                <option value=${clinicName}>${clinicName}</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required">病況類別</label>
                              <select name="Department" class="form-select">
                                <option value="New">近期發病</option>
                                <option value="Old">長期病症</option>
                                <option value="Report">報告查詢</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-group">
                              <label class="required">描述病徵</label>
                              <textarea
                                class="form-control"
                                placeholder="請填寫你的病徵"
                                rows="2"
                              ></textarea>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <div class="form-check text-start">
                              <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="flexCheckDefaultTwo"
                              />
                              <label
                                class="form-check-label"
                                for="flexCheckDefaultTwo"
                              >
                                我同意
                                <a href="#" class="text-decoration-underline"
                                  >條款 &amp; 隱私政策</a
                                >
                              </label>
                            </div>
                          </div>
                          <div class="col-sm-12">
                            <button
                              class="btn btn-primary btn-lg w-100"
                              type="submit"
                            >
                              預約看診
                            </button>
                          </div>
                        </div>
                        <hr />
                        <div class="text-center">
                          <span
                            >已經有帳號了嗎?
                            <a
                              href="./login.html"
                              data-toggle="modal"
                              data-target="#loginModal"
                              >登入</a
                            ></span
                          >
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
                <div>
                  <h4 class="fw-semibold mb-4 text-capitalize">
                    <span class="underline position-relative text-primary">評論</span>
                  </h4>
                  <div class="card shadow border-0 text-inherit flex-fill w-100 mb-4">
                    <div class="card-body p-4">
                      ${commentHtml}
                      <div class="border-0 text-inherit flex-fill w-100">
                      <div>
                        <div id="star-rating" class="text-warning fs-4">
                          <i class="far fa-star" data-value="1"></i>
                          <i class="far fa-star" data-value="2"></i>
                          <i class="far fa-star" data-value="3"></i>
                          <i class="far fa-star" data-value="4"></i>
                          <i class="far fa-star" data-value="5"></i>
                        </div>
                      </div>
                      <div>
                        <textarea class="form-control bg-light border-0" id="comment-text" placeholder="請輸入您的評論內容" rows="3"></textarea>
                      </div>
  
                      <div class="text-end">
                        <button class="btn btn-primary" id="submit-comment">送出評論</button>
                      </div>
                  </div>
                    </div>
                  </div>
                  
                </div>
              </div>
            </div>
          </div>
        </div>`;
  container.append(html);

  let selectedRating = 0;
  $("#star-rating")
    .off("click")
    .on("click", "i", function () {
      selectedRating = Number($(this).data("value"));
      $("#star-rating i").each(function (idx) {
        if (idx < selectedRating) {
          $(this).removeClass("far").addClass("fas");
        } else {
          $(this).removeClass("fas").addClass("far");
        }
      });
    });
}
