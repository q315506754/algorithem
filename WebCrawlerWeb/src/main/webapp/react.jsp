<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/29 0029
  Time: 16:31
  To change this template use File | Settings | File Templates.

  http://localhost:8080/react.jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/no-cache-head.jsp"%>

<html>
<head>
    <title>React</title>

    <link href="assets/css/common.css" rel="stylesheet"/>

</head>


<body>
React page

<div id="container">
</div>

</body>


<%--react.js 是 React 的核心库，--%>
<%--react-dom.js 是提供与 DOM 相关的功能，--%>
<%--Browser.js 的作用是将 JSX 语法转为 JavaScript 语法，这一步很消耗时间，实际上线的时候，应该将它放到服务器完成。--%>

<script src="assets/js/react/react.js" type="text/javascript"></script>
<script src="assets/js/react/react-dom.js" type="text/javascript"></script>
<script src="assets/js/react/browser.min.js" type="text/javascript"></script>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.24/browser.min.js"></script>--%>



<script>

    //basic
//    var ExampleApplication = React.createClass({
//        render: function() {
//            var elapsed = Math.round(this.props.elapsed  / 100);
//            var seconds = elapsed / 10 + (elapsed % 10 ? '' : '.0' );
//            var message =
//                    'React has been successfully running for ' + seconds + ' seconds.';
//
//            return React.DOM.p(null, message);
//        }
//    });
//
//    // Call React.createFactory instead of directly call ExampleApplication({...}) in React.render
//    var ExampleApplicationFactory = React.createFactory(ExampleApplication);
//
//    var start = new Date().getTime();
//    setInterval(function() {
//        ReactDOM.render(
//                ExampleApplicationFactory({elapsed: new Date().getTime() - start}),
//                document.getElementById('container')
//        );
//    }, 50);
    //basic-------------
</script>

<%--注意！！type--%>
<script type="text/babel">
    //jsx-------------
    var ExampleApplication = React.createClass({
        render: function() {
            var elapsed = Math.round(this.props.elapsed  / 100);
            var seconds = elapsed / 10 + (elapsed % 10 ? '' : '.0' );
            var message =
                    'React has been successfully running for ' + seconds + ' seconds.';

            return <p>{message}</p>;
        }
    });
    var start = new Date().getTime();
    setInterval(function() {
        ReactDOM.render(
        <ExampleApplication elapsed={new Date().getTime() - start} />,
                document.getElementById('container')
        );
    }, 50);
    //jsx-------------

</script>
</html>
