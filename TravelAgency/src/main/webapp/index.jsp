
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <title>Турфирма</title>

    <link href="${pageContext.servletContext.contextPath}/css/indexStyle.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.12.1.min.js"></script>
    <script type="text/javascript">
        function show(){
            if (!document.all&&!document.getElementById)
                return
            thelement=document.getElementById? document.getElementById("tick2"): document.all.tick2
            var Digital=new Date()
            var hours=Digital.getHours()
            var minutes=Digital.getMinutes()
            var seconds=Digital.getSeconds()
            var dn=""
            if (hours<24)
                dn=""
            if (hours>24)
                hours=hours-24
            if (hours==0)
                hours=24
            if (minutes<=9)
                minutes="0"+minutes
            if (seconds<=9)
                seconds="0"+seconds
            var ctime=hours+":"+minutes+" "+dn
            thelement.innerHTML=""+ctime+""
            setTimeout("show()",1000)
        }
        window.onload=show
    </script>

            <div>
                <h1>Туристическая фирма "BON voyage"</h1>
            </div>
        <div>
            <h2>Статистика</h2>
        </div>

        <div class="time">
            <h4><span id=tick2></span></h4>
            <h3>
                <script type="text/javascript">
                    var d = new Date();
                    var day=new Array("Воскресенье","Понедельник","Вторник",
                            "Среда","Четверг","Пятница","Суббота");
                    var month=new Array("января","февраля","марта","апреля","мая","июня",
                            "июля","августа","сентября","октября","ноября","декабря");
                    document.write(d.getDate()+ " " + month[d.getMonth()] + ", " + day[d.getDay()]);
                </script>
            </h3>
        </div>

        <div class="content">
            <p class="bold">Договора</p>
            <p>Всего</p>
            <p>За 7 дней</p>
            <p>За 30 дней</p>

        </div>


