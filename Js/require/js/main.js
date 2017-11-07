require.config(
    {
        paths: {
            'jquery': '../../common/assets/js/jquery/jquery-1.8.3.min'
        }
    }
);
require(['jquery'],function ($) {
    $(document).on('click','#contentBtn',function(){
        $('#messagebox').html('You have access Jquery by using require()');

        // require(['desc'],function(desc) {
        //     console.log(JSON.stringify(desc));
        // });

        require(['desc2'],function(desc2) {
            console.log(JSON.stringify(desc2));
        });
    });
});