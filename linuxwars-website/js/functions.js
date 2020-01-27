function command_handler(cmd){
  var command = cmd.split(" ")[0];
  var args = cmd.replace(command + " ", "");
  println(cmd, true);

  if(command == "clear"){
      clear();
      return true;
  }else{
    console.log('{"command":"'+btoa(command)+'","args":"'+btoa(args)+'"}');
    console.log('{"command":"'+command+'","args":"'+args+'"}');
    socket.send('{"command":"'+btoa(command)+'","args":"'+btoa(args)+'"}');
  }
}

function println(message, info, color, bold){
  if(info){
    $('.game-console').append("<div class='command'><xmp style='color:var(--sol-blue);float:left'>"+$('.label-game-commandline').text()+"</xmp><xmp class='color-"+color+" bold-"+bold+"'> "+message+" </xmp></div>");
  } else {
    $('.game-console').append("<xmp class='command color-"+color+" bold-"+bold+"'>"+message+"</xmp>");
  }
  fix_console();
}

function printlnudll(message, info, color){
  //login test test
  $('.command:last').remove();
  println(message, info);
}

function clear(){
  $('.game-console').html("");
  $('.game-console').css("overflow-y", "hidden");
}

function fix_console(){
  $('.game-console').css("max-height", ($(window).height()-35) + "px");
  $('.input-game-commandline').focus();
  if($('.game-console').css("height").replace("px", "") >= ($(window).height()-35)){
    $('.game-console').css("overflow-y", "scroll");
    $('.game-console').scrollTop($('.game-console')[0].scrollHeight);
  }
}
