function DropDown(el) {
	this.dropDown = el;
	this.initEvents();
}

DropDown.prototype = {
	initEvents: function() {
		var obj = this;

		obj.dropDown.on('click', function(event) {
			$(this).toggleClass('active');
			event.stopPropagation();
		});
	}
}

$(function() {

	var dd = new DropDown($('#dd1'));

	$(document).click(function() {
		// all dropdowns
		$('.wrapper-dropdown-5').removeClass('active');
	});

});
