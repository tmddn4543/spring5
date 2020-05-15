var basicDemo = (function () {
    //Adding event listeners
    function _addEventListeners() {
        

        $('#showWindowButton').click(
            function () {
                if( $("#window").css('display') == 'block') {
                    $('#window').jqxWindow('close');
                }else{
                    $('#window').jqxWindow('open');
                }
            }
        );


    };
    //Creating all page elements which are jqxWidgets
    function _createElements() {
        $('#showWindowButton').jqxButton({ width: '70px' });
       
    };
    //Creating the demo window
    function _createWindow() {
        var jqxWidget = $('#jqxWidget');
        var offset = jqxWidget.offset();
        $('#window').jqxWindow({
            position: { x: offset.left + 50, y: offset.top + 50} ,
            showCollapseButton: true, maxHeight: 270, maxWidth: 1600, minHeight: 116.683, minWidth: 250, height: 263, width: 'auto',
            resizable: false,
            autoOpen: false,
            showCollapseButton: false,
            showCloseButton: false,
            initContent: function () {
                $('#window').jqxWindow('focus');
            }
        });
    };
    return {
        config: {
            dragArea: null
        },
        init: function () {
            //Creating all jqxWindgets except the window
            _createElements();
            //Attaching event listeners
            _addEventListeners();
            //Adding jqxWindow
            _createWindow();
        }
    };
} ());
$(document).ready(function () {  
    //Initializing the demo
    basicDemo.init();
});