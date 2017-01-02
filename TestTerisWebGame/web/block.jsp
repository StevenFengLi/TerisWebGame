<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
    //String user = (String) request.getAttribute("username");
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript">

        //游戏区大小定义
        var zone_row_max = 25;
        var zone_col_max = 10;
        //下一方块预览区大小定义
        var next_row_max = 4;
        var next_col_max = 4;

        //当前分数
        var point = 0;
        //消除一行的得分数
        var get_point_per = 100;
        //等级提高一级需要的分数
        var level_point_per = 1000;
        var level = 1;
        //不同等级对应的游戏速度
        var pointLevel = [0,800, 600, 400, 200, 100, 50];

        var contrllor = null;
        var contrllor_next = null;
        var elGameZone =null;
        var elNextZone = null;
        var timer = null;

        var isPaused = false;

        //游戏初始化
        function initGame(){

            elGameZone = document.getElementById('gamezone');
            elNextZone = document.getElementById('nextzone');
            elGameZone.innerHTML = createGameZone(zone_row_max, zone_col_max,false);
            elNextZone.innerHTML = createGameZone(next_row_max, next_col_max,true);

            //firefox下，不支持innerText,只支持textContent
            if(document.getElementById("curLevel").innerText != undefined){
                document.getElementById("curPoint").innerText = "0";
                document.getElementById("curLevel").innerText = "1";
                document.getElementById("score").value = "0";
            }else{
                document.getElementById("curPoint").textContent = "0";
                document.getElementById("curLevel").textContent = "1";
                document.getElementById("score").value = "0";
            }


            document.getElementById("btnPause").disabled = true;
        }

        //开始
        function play(){

            if(!isPaused){

                //游戏区
                contrllor = new Controller(elGameZone,false);
                contrllor.generateBlock(0,4,-1);
                //预览区
                contrllor_next = new Controller(elNextZone,true);
                contrllor_next.generateBlock(0,0,-1);
            }

            document.onkeydown = gameControl;
            timer = window.setInterval(start, pointLevel[level]);


            document.getElementById("btnPlay").disabled = true;
            document.getElementById("btnPause").disabled = false;

            //点击按钮后，焦点转移到了暂停按钮上，导致操作区不响应键盘事件

            if (document.activeElement) {
                document.activeElement.blur();
            }


        }

        //暂停
        function pause(){
            isPaused = true;
            document.getElementById("btnPlay").disabled = false;
            document.getElementById("btnPause").disabled = true;

            timer = window.clearInterval(timer);
            document.onkeydown = null;

        }
        //重新开始
        function restart(){
            contrllor = null;
            contrllor_next = null;
            elGameZone =null;
            elNextZone = null;
            window.clearInterval(timer);
            point = 0;
            level = 1;
            isPaused = false;

            initGame();
            play();
        }

        //键盘控制
        function gameControl(event){
            //在IE9下面，event为未定义，必须从window.event中才能取到。
            var key = window.event ? window.event.keyCode : (event.which ? event.which : event.charCode);
            if(key == 38){
                //上
            }else if(key == 40){
                //下
                contrllor.moveDown();
            }else if(key == 37){
                //左
                contrllor.moveLeft();
            }else if(key == 39){
                //右
                contrllor.moveRight();
            }else if(key == 32){
                //空格 变形
                contrllor.transform();
            }
        }

        //游戏主控逻辑
        function start(){
            if(contrllor.canMoveDown()){
                contrllor.moveDown();
            }else{
                //行消除及下落处理
                contrllor.processRows();
                //生成新的方块元素
                if(contrllor.block.getTop() <=0){
                    alert("Game Over!");

                    window.clearInterval(timer);
                    document.onkeydown = null;
                    return;
                }
                contrllor.block = null;
                contrllor.generateBlock(0,4,contrllor_next.block.block_type);

                //预览区元素擦除
                contrllor_next.easerBlock();
                window.clearInterval(timer);
                //预览区生成新的预览元素
                contrllor_next.generateBlock(0,0,-1);
                timer = window.setInterval(start, pointLevel[level]);

            }
        }
        //创建游戏区和预览显示区
        function createGameZone(row,col,isNext){
            var data = new Array();

            data.push('<table border="1"><tbody>');
            for(var i=0; i < row; i++){
                if(!isNext){
                    data.push('<tr id="row_' + i + '">');
                }else{
                    data.push('<tr id="next_row_' + i + '">');
                }
                for(var j=0; j< col; j++){
                    if(!isNext){
                        data.push('<td id="row_' + i + 'col_' + j + '" class="td_bg"></td>');
                    }else{
                        data.push('<td id="next_row_' + i + 'col_' + j + '" class="td_bg"></td>');
                    }
                }
                data.push('</tr>');
            }
            data.push('</tbody></table>');

            return data.join('');
        }

        //俄罗斯方块元素
        function Block(row, col, block_type){

            this.r = row;
            this.c = col;
            this.block_type = block_type;
            this.transform = 0;
            this.boxes = new Array();

            this.createBoxes = function(boxes_data){
                if(boxes_data != null && boxes_data.length > 0){
                    for(var i=0; i< 4;i++){
                        this.boxes[i] = new Box(boxes_data[i][0], boxes_data[i][1]);
                    }
                }
            }

            this.generateBoxes = function(){
                var boxes_data = {};
                var tf = this.transform % 4;
                switch(block_type){
                    case 0:// ■ ■ ■ ■
                        if(tf == 0 || tf == 2){
                            boxes_data = [[this.r, this.c],[this.r, this.c+1],[this.r, this.c+2],[this.r, this.c+3]];
                        }else if(tf == 1 || tf == 3){
                            boxes_data = [[this.r, this.c],[this.r+1, this.c],[this.r+2, this.c],[this.r+3, this.c]];
                        }
                        break;
                    case 1:
                        //■ ■
                        //■ ■
                        boxes_data = [[this.r, this.c],[this.r, this.c+1],[this.r+1, this.c],[this.r+1, this.c+1]];
                        break;
                    case 2:// ■ ■ ■
                        //   ■
                        if(tf == 0){
                            boxes_data = [[this.r, this.c],[this.r, this.c+1],[this.r, this.c+2],[this.r+1, this.c+1]];
                        }else if(tf == 1){
                            //  ■
                            //■ ■
                            //  ■
                            boxes_data = [[this.r, this.c+1],[this.r+1, this.c],[this.r+1, this.c+1],[this.r+2, this.c+1]];
                        }else if(tf == 2){
                            //  ■
                            //■ ■ ■
                            boxes_data = [[this.r, this.c+1],[this.r+1, this.c],[this.r+1, this.c+1],[this.r+1, this.c+2]];
                        }else if(tf == 3){
                            //  ■
                            //  ■ ■
                            //  ■
                            boxes_data = [[this.r,this.c],[this.r+1, this.c],[this.r+1, this.c+1],[this.r+2, this.c]];
                        }
                        break;
                    case 3:// ■ ■
                        //   ■ ■
                        if(tf == 0 || tf == 2){
                            boxes_data = [[this.r, this.c],[this.r, this.c+1],[this.r+1, this.c+1],[this.r+1, this.c+2]];
                        }else if(tf == 1 || tf == 3){
                            //  ■
                            //■ ■
                            //■
                            boxes_data = [[this.r, this.c+1],[this.r+1, this.c],[this.r+1, this.c+1],[this.r+2, this.c]];
                        }
                        break;
                    case 4://   ■ ■
                        // ■ ■
                        if(tf == 0 || tf == 2){
                            boxes_data = [[this.r, this.c+1],[this.r, this.c+2],[this.r+1, this.c],[this.r+1, this.c+1]];
                        }else if(tf == 1 || tf == 3){
                            //  ■
                            //  ■ ■
                            //    ■
                            boxes_data = [[this.r, this.c],[this.r+1, this.c],[this.r+1, this.c+1],[this.r+2, this.c+1]];
                        }
                        break;
                    case 5://   ■
                        //   ■
                        // ■ ■
                        if(tf == 0){
                            boxes_data = [[this.r, this.c+1],[this.r+1, this.c+1],[this.r+2, this.c+1],[this.r+2, this.c]];
                        }else if(tf == 1){
                            //
                            //■
                            //■ ■ ■
                            boxes_data = [[this.r, this.c],[this.r+1, this.c],[this.r+1, this.c+1],[this.r+1, this.c+2]];
                        }else if(tf == 2){
                            // ■ ■
                            // ■
                            // ■
                            boxes_data = [[this.r, this.c],[this.r, this.c+1],[this.r+1,this.c],[this.r+2, this.c]];
                        }else if(tf == 3){
                            // ■ ■ ■
                            //     ■
                            boxes_data = [[this.r, this.c],[this.r, this.c+1],[this.r, this.c+2],[this.r+1, this.c+2]];
                        }
                        break;
                    case 6://   ■
                        //   ■
                        //   ■ ■
                        if(tf == 0){
                            boxes_data = [[this.r, this.c],[this.r+1,this.c],[this.r+2, this.c],[this.r+2, this.c+1]];
                        }else if(tf == 1){
                            //
                            //■ ■ ■
                            //■
                            boxes_data = [[this.r, this.c],[this.r, this.c+1],[this.r, this.c+2],[this.r+1, this.c]];
                        }else if(tf == 2){
                            //■ ■
                            //  ■
                            //  ■
                            boxes_data = [[this.r, this.c],[this.r, this.c+1],[this.r+1, this.c+1],[this.r+2, this.c+1]];
                        }else if(tf == 3){
                            //     ■
                            // ■ ■ ■
                            //
                            boxes_data = [[this.r, this.c+2],[this.r+1, this.c],[this.r+1, this.c+1],[this.r+1, this.c+2]];
                        }
                        break;
                }

                return boxes_data;

            }

            this.mvR = function(){
                if(this.boxes != null){
                    for(var i=0,length=this.boxes.length; i<length; i++){
                        this.boxes[i].mvR();
                    }
                    this.c++;
                }
            }

            this.mvD = function(){
                if(this.boxes != null){
                    for(var i=0,length=this.boxes.length; i<length; i++){
                        this.boxes[i].mvD();
                    }
                    this.r++;
                }

            }

            this.mvL = function(){
                if(this.boxes != null){
                    for(var i=0,length=this.boxes.length; i<length; i++){
                        this.boxes[i].mvL();
                    }
                    this.c--;
                }
            }
            //最左边
            this.getLeft = function(){
                return Math.min(Math.min(this.boxes[0].col, this.boxes[1].col),Math.min(this.boxes[2].col, this.boxes[3].col));
            }
            //最右边
            this.getRight = function(){
                return Math.max(Math.max(this.boxes[0].col, this.boxes[1].col),Math.max(this.boxes[2].col, this.boxes[3].col));
            }
            //最底边
            this.getBottom = function(){
                return Math.max(Math.max(this.boxes[0].row, this.boxes[1].row),Math.max(this.boxes[2].row, this.boxes[3].row));
            }
            //最顶边
            this.getTop = function(){
                return Math.min(Math.min(this.boxes[0].row, this.boxes[1].row),Math.min(this.boxes[2].row, this.boxes[3].row));
            }
            //是否当前Block的Box
            this.isCurBox = function(row, col){
                for(var i=0; i < this.boxes.length; i++){
                    if(this.boxes[i].row == row && this.boxes[i].col == col){
                        return true;
                    }
                }

                return false;

            }
        }

        //Box 方块元素中的其中一个元素
        function Box(row, col){
            this.row = row;
            this.col = col;

            this.mvR = function(){
                this.col++;
            }
            this.mvD = function(){
                this.row++;
            }
            this.mvL = function(){
                this.col--;
            }

        }

        function Controller(domEle,isNext){
            this.domEle = domEle;
            this.block = null;
            this.isNext = isNext;

            //生成方块元素
            this.generateBlock = function(row, col,type){
                var block_type;
                //第一次随机生成，以后按照预览区方块生成
                if(type == -1){
                    block_type = parseInt(Math.random() * (6-0+1) + 0);
                }else{
                    block_type = type;
                }
                this.block = new Block(row, col, block_type);
                this.block.createBoxes(this.block.generateBoxes());
                this.dispBlock();


            }

            //变形
            this.transform = function(){
                if(this.block != null){
                    if(this.canTransform()){
                        this.easerBlock();
                        this.block.transform++;
                        this.block.createBoxes(this.block.generateBoxes());
                        this.dispBlock();
                    }
                }

            }

            //预生成一个变形后的Boxes数据，判断其所在的方格是否有方块，判断完后恢复原始的变形计数
            this.canTransform = function(){
                var org_tf = this.block.transform;
                this.block.transform++;
                var new_boxes_data = this.block.generateBoxes();

                for(var i=0; i< 4;i++){

                    var r = new_boxes_data[i][0];
                    var c = new_boxes_data[i][1];

                    //变形后的方块超出边界，变形不可，返回
                    if(r >= zone_row_max || r < 0 || c >= zone_col_max || c < 0){
                        this.block.transform = org_tf;
                        return false;
                    }

                    id = "row_" + r + "col_" + c;
                    if(!this.block.isCurBox(r,c)){
                        if(document.getElementById(id)){
                            if(document.getElementById(id).className == "td_block"){
                                this.block.transform = org_tf;
                                return false;
                            }
                        }
                    }
                }

                this.block.transform = org_tf;
                return true;
            }

            //方块显示
            this.dispBlock = function(){
                if(this.block != null){
                    for(var i=0; i < this.block.boxes.length; i++){
                        var r = this.block.boxes[i].row;
                        var c = this.block.boxes[i].col;
                        var id="";
                        if(!isNext){
                            id = "row_" + r + "col_"+ c;
                        }else{
                            id = "next_row_" + r + "col_"+ c;
                        }
                        document.getElementById(id).className = "td_block";
                    }
                }
            }
            //擦除方块
            this.easerBlock = function(){
                if(this.block != null){
                    for(var i=0; i < this.block.boxes.length; i++){
                        var r = this.block.boxes[i].row;
                        var c = this.block.boxes[i].col;
                        var id="";
                        if(!isNext){
                            id = "row_" + r + "col_"+ c;
                        }else{
                            id = "next_row_" + r + "col_"+ c;
                        }
                        document.getElementById(id).className = "td_bg";
                    }
                }
            }
            //下移
            this.moveDown = function(){
                if(this.block != null){
                    if(this.canMoveDown()){
                        this.easerBlock();
                        this.block.mvD();
                        this.dispBlock();
                    }
                }
            }
            this.canMoveDown = function(){
                var canMovedown = this.block.getBottom() < (zone_row_max-1);
                if(!canMovedown) return false;
                //下一行如果有方块，不能移动
                return this.canMove('D');
            }
            //能否向右，向下，向左移动
            this.canMove = function(side){
                var canMovedown = true;
                var boxes = this.block.boxes;
                for(var i=0; i < boxes.length; i++){
                    var r = boxes[i].row;
                    var c = boxes[i].col;
                    var id  = "";
                    switch(side){
                        case "D":
                            id = "row_" + (r+1) + "col_" + c;
                            r = r+1;
                            break;
                        case "R":
                            id = "row_" + r + "col_" + (c+1);
                            c = c + 1;
                            break;
                        case "L":
                            id = "row_" + r + "col_" + (c-1);
                            c = c - 1;
                            break;
                    }

                    if(!this.block.isCurBox(r,c)){
                        if(document.getElementById(id).className == "td_block"){
                            return false;
                        }else{
                            canMovedown = canMovedown && true;
                        }
                    }
                }

                return canMovedown;
            }
            //右移
            this.moveRight = function(){
                if(this.block != null){
                    if(this.canMoveRight()){
                        this.easerBlock();
                        this.block.mvR();
                        this.dispBlock();
                    }
                }
            }
            this.canMoveRight = function(){
                var canMovedown = this.block.getRight() < (zone_col_max-1);
                if(!canMovedown) return false;
                //右边如果有方块，不能移动
                return this.canMove('R');
            }
            //左移
            this.moveLeft = function(){
                if(this.block != null){
                    if(this.canMoveLeft()){
                        this.easerBlock();
                        this.block.mvL();
                        this.dispBlock();
                    }
                }
            }
            this.canMoveLeft = function(){
                var canMovedown = this.block.getLeft() > 0;
                if(!canMovedown) return false;
                //左边如果有方块，不能移动
                return this.canMove('L');
            }

            //行消除及下落
            this.processRows = function(){
                for(var r=zone_row_max-1; r >=0; r--){
                    //可以消除
                    if(this.isFullRow(r)){
                        //消除
                        this.delRow(r);
                        //积分计算
                        point += get_point_per;
                        //等级计算
                        level = parseInt((point / level_point_per) + 1);
                        level = (level > (pointLevel.length -1)) ? (pointLevel.length-1) : level;

                        if(document.getElementById("curLevel").innerText != undefined){
                            document.getElementById("curPoint").innerText =  "" + point;
                            document.getElementById("curLevel").innerText = "" + level;
                            document.getElementById("score").value = point.toString();
                        }else{
                            document.getElementById("curPoint").textContent = "" + point;
                            document.getElementById("curLevel").textContent = "" + level;
                            document.getElementById("score").value = point.toString();
                        }

                        //方块下落处理
                        for(var i=r; i >=1; i--){
                            var j = (i-1)>0 ? (i-1) : 0;
                            for(var m = 0; m < zone_col_max; m++){
                                var des_id = "row_" + i + "col_" + m;
                                var org_id = "row_" + j + "col_" + m;

                                document.getElementById(des_id).className = document.getElementById(org_id).className;
                            }
                        }

                        r = r + 1;
                    }
                }
            }

            this.delRow = function(row){
                for(var i=0; i < zone_col_max; i++){
                    var id = "row_" + row + "col_" + i;
                    document.getElementById(id).className = "td_bg";
                }
            }

            this.isThisRowHasBox = function(row){
                var hasBox = false;
                for(var i=0; i < zone_col_max; i++){
                    var id = "row_" + row + "col_" + i;
                    if(document.getElementById(id).className == "td_block"){
                        return true;
                    }

                }
                return hasBox;
            }

            this.isFullRow = function(row){
                var hasBox = true;
                for(var i=0; i < zone_col_max; i++){
                    var id = "row_" + row + "col_" + i;
                    if(document.getElementById(id).className != "td_block"){
                        return false;
                    }

                }
                return hasBox;
            }

        }

    </script>
    <style>
        body {
            margin: 0 auto;
            font-size: 12px;
            font-family: Verdana;
            line-height: 1.5;
            width: 54%;
            height: 90%;
            margin-left: 446px;
        }
        h1{
            /* margin: 0; */
            width: 35%;
            /* padding-left: 23px; */
            position: relative;
            left: 128px;
        }

        ul, dl, dd, h1, h2, h3, h4, h5, h6, form, p {
            padding: 0;
            margin: 0;
        }
        ul {
            list-style: none;
        }
        table{border-collapse:collapse;}
        td{width:20px; height:20px;border-left:0;border-top:0;border-right:1px solid #00cd11;border-bottom:1px solid #00cd11;border-collapse:collapse;}
        .td_bg { background-color:#eee;}
        .td_fullrow { background-color:#FF0;}
        .td_block{background-color:red;}
        .btn{display:block; width:150px;height:30px; margin:10px;}
        #main{float:left; padding:15px; border:1px solid #eee; background-color:#000;height:520px;}
        #control_side{float:left;padding-top:15px; margin:0 auto;width:200px; height:535px;border:1px solid #eee;background-color:#444;}
        #nextzone{ padding:15px;margin: 0px auto; }
        #gameControl{ padding:15px;}
        #pointDisplay{ padding:5px 10px 10px 15px;color:#FFF;}
        #levelDisplay{ padding:5px 10px 10px 15px;color:#FFF;}
        #manual{ margin:0 auto;padding:5px 15px 0px 25px;color:#FFF;font-size:11px;}

        #point{ font-size:18px;font-weight:bold; height:40px; color:#F00;text-align:center; border:1px solid #FFF;}
        #level{ font-size:18px;font-weight:bold; height:40px; color:#F00;text-align:center;border:1px solid #FFF;}

    </style>
</head>
<body onload="initGame();">
<h1>Welcome,${username }!</h1>
<div id="main">
    <div id="gamezone">
    </div>
</div>
<div id="control_side">
    <div id="nextzone">
    </div>
    <div>

        <div id="gameControl">
            <input type="button" name="start" value="开始" id="btnPlay" onclick="play()" class="btn" />
            <input type="button" name="start" value="暂停" id="btnPause" onclick="pause()" class="btn" />
            <input type="button" name="start" value="重新开始" id="btnRestart" onclick="restart()" class="btn" />
           <div style="float:left">
            <form action="ScoreServlet" method="post">
                <input name="username" type="hidden" value=${username}>>
                <input type="hidden" name="score" id="score" value="0">
                <input type="submit" value="提交分数">
            </form>
           </div>
            <div style="float:right">
            <form action="RankLocalServlet" method="get">
                <input type="submit" value="本地排行榜" >
                <!--<input type="button" name="start" value="查看排行榜" id=‘btnRank" onclick="window.location='rank.jsp'" class="btn" />-->
            </form>
            </div>
<div style="clear:both"></div>
        </div>
        <div id="pointDisplay">
            <span>目前得分:</span>
            <div id="point">
                <span id="curPoint"></span>
            </div>
        </div>

        <div id="levelDisplay">
            <span>目前等级:</span>
            <div id="level">
                <span id="curLevel"></span>
            </div>
        </div>

        <div id="manual">
            <ul>
                <li>→ 向右移动</li>
                <li>← 向左移动</li>
                <li>↓ 加速向下</li>
                <li>空格 变换</li>
                <li><font color="red" size="1.5"> ${msg3 }</font></li>
            </ul>
        </div>
    </div>
</div>
</body>
</div>
</html>