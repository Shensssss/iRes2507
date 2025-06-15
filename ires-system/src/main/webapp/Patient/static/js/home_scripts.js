$(function () {
  // ------------ team images width same height -----------
  // var images = $(".tc-team-style1 .team-card .img, .img_sm_h");
  // images.each(function () {
  //   var width = $(this).width();
  //   $(this).height(width);
  // });

  // --------- portfolio effect ---------
  $(".tc-portfolio-st7 .works .item").on("mouseenter", function () {
    $(this).siblings().removeClass("active");
    $(this).addClass("active");
  });
});

// ------------ swiper sliders -----------
$(document).ready(function () {
  // ------------ tc-header-st7 marq-slider -----------
  var swiper = new Swiper(".tc-header-st7 .mark-sliders .slider", {
    slidesPerView: "auto",
    spaceBetween: 150,
    centeredSlides: true,
    pagination: false,
    navigation: false,
    mousewheel: false,
    keyboard: true,
    speed: 20000,
    allowTouchMove: false,
    autoplay: {
      delay: 1,
    },
    loop: true,
  });

  // ------------ tc-services-slider1 -----------
  var swiper = new Swiper(".tc-services-st7 .services-slider", {
    slidesPerView: 3,
    spaceBetween: 20,
    centeredSlides: true,
    speed: 1500,
    pagination: {
      el: ".tc-services-st7 .swiper-pagination",
      clickable: true,
    },
    navigation: false,
    mousewheel: false,
    keyboard: true,
    autoplay: {
      delay: 6000,
    },
    loop: true,
    breakpoints: {
      0: {
        slidesPerView: 1,
      },
      480: {
        slidesPerView: 2,
      },
      787: {
        slidesPerView: 3,
      },
      991: {
        slidesPerView: 3,
      },
      1200: {
        slidesPerView: 3,
      },
    },
  });

  // ------------ tc-testimonials-st7 -----------
  var swiper = new Swiper(".tc-testimonials-st7 .testi-slider", {
    slidesPerView: 1,
    spaceBetween: 20,
    // centeredSlides: true,
    speed: 1500,
    pagination: {
      el: ".tc-testimonials-st7 .swiper-pagination",
      clickable: true,
    },
    navigation: false,
    mousewheel: false,
    keyboard: true,
    autoplay: {
      delay: 5000,
    },
    loop: true,
  });

  // 根據登入狀態切換 Menu 或 Login
  const patient = sessionStorage.getItem("patient");
  if (patient) {
    $("#login-link").addClass("d-none");
    $("#menu-link").removeClass("d-none");
  } else {
    $("#login-link").removeClass("d-none");
    $("#menu-link").addClass("d-none");
  }

  // 附近診所取得
  loadClinics("#all_clinc");
  loadClinics("#peds", 1);
  loadClinics("#dds", 2);
  loadClinics("#oph", 3);
  loadClinics("#obgyn", 4);
  loadClinics("#derm", 5);
  loadClinics("#ent", 6);
  loadClinics("#psych", 7);
  loadClinics("#tcm", 8);
  loadClinics("#nutrition", 9);
  loadClinics("#pt", 10);
});

// ------------ gsap scripts -----------
$(function () {
  gsap.registerPlugin(ScrollTrigger, ScrollSmoother);

  // create the smooth scroller FIRST!
  const smoother = ScrollSmoother.create({
    content: "#scrollsmoother-container",
    smooth: 1.5,
    normalizeScroll: true,
    ignoreMobileResize: true,
    effects: true,
    //preventDefault: true,
    //ease: 'power4.out',
    //smoothTouch: 0.1,
  });
});
$(document).ready(function () {
  $.ajax({
    url: "/ires-system/major/list",
    method: "GET",
    success: function (data) {
      console.log(data);
      if (Array.isArray(data)) {
        const $select = $("#major-select");
        const $ul = $(".custom-select-options ul");

        $select.empty();
        $ul.empty();

        $select.append('<option value="all" selected>全部</option>');
        $ul.append('<li data-value="all" class="is-highlighted">全部</li>');

        data.forEach(function (item) {
          const option = $("<option>", {
            value: item.majorId,
            text: item.majorName,
          });
          $select.append(option);

          const li = $("<li>", {
            "data-value": item.majorId,
            text: item.majorName,
          });
          $ul.append(li);
        });
      }
    },
  });
  $("#distance-range").on("input", function () {
    $("#distance-value").text($(this).val());
  });
});

function loadClinics(selector, majorId = null) {
  const url = majorId
    ? `/ires-system/clinicMajor/list?majorId=${majorId}`
    : "/ires-system/clinicMajor/list";

  $.ajax({
    url,
    method: "GET",
    success: function (clinics) {
      if (Array.isArray(clinics)) {
        const $wrapper = $(`${selector} .swiper-wrapper`);
        $wrapper.empty();

        for (let i = 0; i < clinics.length; i += 3) {
          let groupHtml = "";
          for (let j = i; j < i + 3 && j < clinics.length; j++) {
            const clinic = clinics[j];
            groupHtml += `
              <div class="swiper-slide card">
                <div class="card-img">
                  <img src="static/img/1.jpeg" alt="" />
                </div>
                <div class="card-content">
                  <span><h3>${clinic.clinicName}</h3></span>
                  <div class="rating">
                    <i class="fa-solid fa-star" style="color: gold"></i>
                    4.5 Rating |
                    <div class="calender">
                      <svg width="23" height="23" viewBox="0 0 32 32" fill="none">
                        <path d="M10.6667 4C11.4031 4 12 4.59696 12 5.33333V8C12 8.73637 11.4031 9.33333 10.6667 9.33333C9.9303 9.33333 9.33334 8.73637 9.33334 8V5.33333C9.33334 4.59696 9.9303 4 10.6667 4Z" fill="black"/>
                        <path d="M21.3333 4C22.0697 4 22.6667 4.59696 22.6667 5.33333V8C22.6667 8.73637 22.0697 9.33333 21.3333 9.33333C20.5969 9.33333 20 8.73637 20 8V5.33333C20 4.59696 20.5969 4 21.3333 4Z" fill="black"/>
                        <rect x="4.75" y="6.75" width="22.5" height="20.5" rx="3.25" stroke="black" stroke-width="1.5"/>
                      </svg>
                    </div>
                    目前號碼: 1
                  </div>
                  <div class="price-and-button">
                    <div>
                      <div class="price">1 <span>km</span></div>
                    </div>
                    <div style="vertical-align: bottom; display: flex; gap: 10px;">
                      <a href="#" class="view-room-btn">上午\n${clinic.morning}</a>
                      <a href="#" class="view-room-btn">下午\n${clinic.afternoon}</a>
                      <a href="#" class="view-room-btn">晚上\n${clinic.night}</a>
                    </div>
                  </div>
                </div>
              </div>
            `;
          }
          $wrapper.append(groupHtml);
        }

        new Swiper(`${selector} .room-slider`, {
          slidesPerView: 3,
          spaceBetween: 20,
          slidesPerGroup: 3,
          navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
          },
          breakpoints: {
            0: { slidesPerView: 1, slidesPerGroup: 1 },
            768: { slidesPerView: 2, slidesPerGroup: 2 },
            1200: { slidesPerView: 3, slidesPerGroup: 3 },
          },
        });
      }
    },
  });
}
