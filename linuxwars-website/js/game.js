var command_history = ['', ''];
var command_history_cursor = 0;
var password = false;


var socket = new WebSocket("ws://localhost:" + 8080 + "/websocket");

socket.onmessage = function(event) {
  request = JSON.parse(event.data);
  if(request.type == "println"){
    println(atob(request.message), false, request.color);
  }else if(request.type == "updatelabel"){
    label = atob(request.message);
    $('.label-game-commandline').text(label);
  }
};

socket.onopen = function(event) {
  println("..connection established. Let's go!");
  socket.send('{"command":"'+btoa("motd")+'","args":""}');
};

socket.onclose = function(event) {
  println("...connection lost! ☣ PLEASE PANIC ☣", false, "red");
};

$('.input-game-commandline').keydown(function(event){
  var key = (event.keyCode ? event.keyCode : event.which);
  if(key == 13){ // ENTER PRESSED
    var command = $('.input-game-commandline').val();
    command_handler(command);
    if(command.length == 0){
      println("", true);
    }else{
      $('.input-game-commandline').val("");
      if(command_history[command_history.length - 2] != command){
        command_history.splice(command_history.length - 1, 0, command);
        if(command_history.length > (20+2)){
          command_history.splice(1, 1);
        }
      }
    }
  }

  if(key == 38){ // UP KEY
      if(command_history_cursor > 0){
        command_history_cursor -= 1;
      }
      $('.input-game-commandline').val(command_history[command_history_cursor]);
  }else if(key == 40){ // DOWN KEY
      if(command_history_cursor + 1 < command_history.length){
        command_history_cursor += 1;
      }
      $('.input-game-commandline').val(command_history[command_history_cursor]);
  }else{
      command_history_cursor = command_history.length - 1;
    }
});

$('.game-commandline').on('click', function(){
  $('.input-game-commandline').focus();
});

$(document).ready(function(){
  println("Connecting to Server...");
  fix_console();

  $( window ).resize(function() {
    fix_console();
  });
  time = (new Date().getTime());

  (function my_func() {

    setTimeout( my_func, 1000 );
  })();
});
