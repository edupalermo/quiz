jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", Math.max(0, (1 * ($(window).height() - $(this).outerHeight()) / 3) + $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
    return this;
}
