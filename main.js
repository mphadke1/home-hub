$(document).ready(function() {
    console.log("TEST");
    var navHeight = $('nav').outerHeight(); // get height of nav tag
    $('ul a').click(function (e) {
        e.preventDefault();
        var target = $(this.hash);
        $('html, body').animate({
            scrollTop: target.offset().top - navHeight // scroll to h3 minus height of nav
        }, 500);
        return false;
    });

    $("#datetimepicker").datetimepicker()
});