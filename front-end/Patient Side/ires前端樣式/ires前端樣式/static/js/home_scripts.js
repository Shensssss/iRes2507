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
    })


});


// ------------ swiper sliders -----------
$(document).ready(function () {


    // ------------ tc-header-st7 marq-slider -----------
    var swiper = new Swiper('.tc-header-st7 .mark-sliders .slider', {
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
    var swiper = new Swiper('.tc-services-st7 .services-slider', {
        slidesPerView: 3,
        spaceBetween: 20,
        centeredSlides: true,
        speed: 1500,
        pagination: {
            el: '.tc-services-st7 .swiper-pagination',
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
            }
        }
    });


    // ------------ tc-testimonials-st7 -----------
    var swiper = new Swiper('.tc-testimonials-st7 .testi-slider', {
        slidesPerView: 1,
        spaceBetween: 20,
        // centeredSlides: true,
        speed: 1500,
        pagination: {
            el: '.tc-testimonials-st7 .swiper-pagination',
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



