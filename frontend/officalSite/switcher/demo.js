// --------------------------------------------------
// demo.js by ThemeModern 2016
// --------------------------------------------------

jQuery(document).ready(function($){
	'use strict';
	
	jQuery(".color1").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/aqua.css");
	});
	
	jQuery(".color2").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/blue.css");
	});
	
	jQuery(".color3").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/green.css");
	});
	
	jQuery(".color4").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/grey.css");
	});
	
	jQuery(".color5").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/orange.css");
	});
	
	jQuery(".color6").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/pink.css");
	});
	
	jQuery(".color7").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/purple.css");
	});
	
	jQuery(".color8").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/red.css");
	});
	
	jQuery(".color9").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/yellow.css");
	});
	
	jQuery(".color10").click(function(){
		jQuery("#colors").attr("href", "switcher/colors/lime.css");
	});
	
	jQuery(".custom-show").hide();
	
	jQuery(".custom-close").click(function(){
		jQuery(this).hide();
		jQuery(".custom-show").show();
		jQuery('#switcher').animate({'left': '+=200px'},'medium');
	});
  	
	jQuery(".custom-show").click(function(){
		jQuery(this).hide();
		jQuery(".custom-close").show();
		jQuery(this).parent().animate({'left': '-=200px'},'medium');
	});

	jQuery('#tm-layout-switch').on('change', function() {
		if($('#tm-layout-switch').val()=='wide'){
			$('.compact-boxed-bg').removeAttr('style');
			$('body').removeClass('compact-boxed-bg');			
		}else if($('#tm-layout-switch').val()=='boxed'){
			$('body').addClass('compact-boxed-bg');
			$('.compact-boxed-bg').css('background-image', 'url(switcher/images/patern01.png)');
		}
	});
	
	jQuery('#tm-boxed-bg li').click(function(){		
		if($('#tm-layout-switch').val()!="boxed"){
			alert('Please select boxed layout first.');
		}			
	});

	$('#switcher ul [class*="bg"]').click(function() {
		$('.compact-boxed-bg').css('background-image', 'url(switcher/images/patern' + $(this).attr('data-value') + '.png)');
	});

	$('#switcher ul [class*="jpg"]').click(function() {
		$('.compact-boxed-bg').css('background-image', 'url(switcher/images/patern' + $(this).attr('data-value') + '.jpg)');
	});

});

