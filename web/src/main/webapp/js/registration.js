$( '#registration' ).validate({
  rules: {
    password: {
      required: true,
      minlength: 8,
    },
    email: {
      email: true,
      remote: {
        url: location.href.substring(0,location.href.lastIndexOf('/'))+"/checkEmail.do",
        type: "post",
        data: {
          email: function() {
            return $( "#email" ).val();
          }
        }
      }
    },
    company: "optional"
  },
  messages: {
      email: {
        remote: jQuery.format('Email is already in use')
      }
    },
  highlight: function(element) {
    $(element).closest('.control-group').removeClass('success').addClass('error');
  },
  success: function(element) {
    element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').addClass('success');
  },
  error: function(element) {
    $('#singlebutton').attr('disabled', 'disabled');
  },
  submitHandler: function(form){
    if(!this.wasSent){
      this.wasSent = true;
      $(':submit', form).val('Please wait...')
                        .attr('disabled', 'disabled')
                        .addClass('disabled');
      form.submit();
    } else {
      return false;
    }
  },
  onkeyup: function(element, event) {
    if (event.which === 9 && this.elementValue(element) === "") {
        return;
    } else {
        this.element(element);
    }
  }
});
