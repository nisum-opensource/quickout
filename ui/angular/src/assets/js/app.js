/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
$(function() {
   $(".store-tabs li").click(function(e) {
      var tabclass = $(this).attr('id');
      $(".shipping-contact-form .tab-sections").hide();
      $("#" + tabclass + "_section").show();
      $(".store-tabs li").removeClass("active");
      $(this).addClass("active");
   });

   $(".help-tip").click(function(e) {
      e.preventDefault();
      var tid = $(this).attr("id");
      $("#d_" + tid).fadeIn('slow');
   });

   $(".close-tip").click(function(e) {
      e.preventDefault();
      $(this).parent().fadeOut('slow');

   });

   $("#enterpromocodelink").click(function(e) {
      e.preventDefault();
      $(this).hide();
      $("#promoFieldDiv").show();
   });   
});