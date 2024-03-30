$("#contactForm").validator().on("submit", function (event) {
    if (event.isDefaultPrevented()) {
        // handle the invalid form...
        messageError();
        messageSubmit(false, "Did you fill in the form properly?");
    } else {
        // everything looks good!
        event.preventDefault();
        submitData();
    }
});


function submitData(){
    // Initiate Variables With Form Content
    var name = $("#name").val();
    var email = $("#email").val();
    var msg_subject = $("#msg_subject").val();
    var message = $("#message").val();


    $.ajax({
        type: "POST",
        url: "php/form-process.php",
        data: "name=" + name + "&email=" + email + "&msg_subject=" + msg_subject + "&message=" + message,
        success : function(text){
            if (text == "success"){
                messageSuccess();
            } else {
                messageError();
                messageSubmit(false,text);
            }
        }
    });
}

const messageSuccess = () => {
    $("#contactForm")[0].reset();
    messageSubmit(true, "Message Submitted!")
}


const messageError = () => {
    $("#contactForm").removeClass().addClass('shake animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
        $(this).removeClass();
    });
}

const messageSubmit = (check, msg) =>{
    var messageClas = '';
    if(!check){
        messageClas = "h3 text-center text-danger";
    } else {
        messageClas = "h3 text-center tada animated text-success";
    }
    $("#msgSubmit").removeClass().addClass(messageClas).text(msg);
}