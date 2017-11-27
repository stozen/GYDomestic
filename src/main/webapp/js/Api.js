//var baseUrl = "http://192.168.0.166:8080/GYDomestic/";
var baseUrl = "http://23sdk.23h5.cn:8080/GYDomestic/";

function loadXMLDoc(method, url, body, response) {

    var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    xmlhttp.onreadystatechange = function () {
        var json = {};
        if (xmlhttp.readyState === 4) {
            if (xmlhttp.status === 200) {
                json = JSON.parse(xmlhttp.response);
            } else {
                json.status = "0" + xmlhttp.status;
            }
            if (json.status === "0208") {
                upDataToken(json.token);
                loadXMLDoc(method, url, body, response);
                return;
            }
            if (json.status === "0405") {
                loginOut();
                return;
            }
            response(json);
        }

    };
    xmlhttp.open(method, url, true);
    xmlhttp.setRequestHeader("Content-Type", "application/json;");
    if (isLogin()) {
        var user = loadUser();
        xmlhttp.setRequestHeader("id", user.userId + "");
        xmlhttp.setRequestHeader("token", user.token);

    }
    if (body !== null) {
        xmlhttp.send(JSON.stringify(body));
    }

}

// 登录
function login(body, response) {
    loadXMLDoc("POST", baseUrl + "user/login", body, response)
}

//注册
function regist(body, response) {
    loadXMLDoc("POST", baseUrl + "user/register", body, response)
}

//获取验证码
function validCode(body, phone, response) {
    loadXMLDoc("GET", baseUrl + "getDomesticCode/send?phone=" + phone, body, response)
}

//创建订单
function createOrder(body, response) {
    loadXMLDoc("POST", baseUrl + "order/create", body, response)
}

//阿里支付
function alipayOrder(body, response) {
    loadAlipay("POST", baseUrl + "alipay/phonepay", body, response)
}

//忘记密码第一步
function forgetFirst(body, response) {
    loadXMLDoc("PUT", baseUrl + "user/forgetpassone", body, response)
}

//忘记密码第二步
function forgetNext(body, response) {
    loadXMLDoc("PUT", baseUrl + "user/forgetpasstwo", body, response)
}
//验证支付
function validationPay(body, response) {
    loadXMLDoc("POST", baseUrl + "alipay/signali", body, response)
}

var wait = 60;

function time(o) {
    if (wait === 0) {
        o.removeAttribute("disabled");
        o.value = "获取验证码";
        wait = 60;
    } else {

        o.setAttribute("disabled", true);
        o.value = "重新发送(" + wait + ")";
        wait--;
        setTimeout(function () {
                time(o)
            },
            1000)
    }
}

function loadAlipay(method, url, body, response) {
    var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    xmlhttp.onreadystatechange = function () {
        var json = {};
        if (xmlhttp.readyState === 4) {
            if (xmlhttp.status === 200) {
                json = xmlhttp.response;
            } else {
                json.status = "0" + xmlhttp.status;
            }
            if (json.status === "0208") {
                upDataToken(json.token);
                loadXMLDoc(method, url, body, response);
                return;
            }
            response(json)
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.setRequestHeader("Content-Type", "application/json;");
    if (isLogin()) {
        var user = loadUser();
        xmlhttp.setRequestHeader("id", user.userid + "");
        xmlhttp.setRequestHeader("token", user.token);

    }
    xmlhttp.send(JSON.stringify(body));
}

/**
 * 用户相关
 */
//是否登录
function isLogin() {
    return this.loadUser() !== null
}

//保存用户
function saveUser(user) {
    var storage = window.localStorage;
    storage.setItem('user', JSON.stringify(user))
}

//保存key
function saveKey(key) {
    var storage = window.localStorage;
    storage.setItem('key', JSON.stringify(key))
}
//获取key
function getKey() {
    var key = window.localStorage.getItem('key');
    if (key === undefined || key === null) {
        return null
    }
    return JSON.parse(key);
}


function loadUser() {
    var user = window.localStorage.getItem('user');
    if (user === undefined || user === null) {
        return null
    }
    return JSON.parse(user);
}

function relogin() {
    loginOut();
}

function upDataToken(token) {
    var user = loadUser();
    user.token = token;
    saveUser(user);
}

function loginOut() {
    window.localStorage.removeItem('user')
}
/**
 * 支付相关
 */
function savePay(product) {
    window.sessionStorage.setItem("product", JSON.stringify(product));
}

function getProduct() {
    var product = window.sessionStorage.getItem('product');
    if (product === undefined || product === null) {
        return null
    }
    return JSON.parse(product);
}

function productIsEmpty(product) {
    if (product === null) return true;
    if (product.price === undefined || product.price === '' || product.price === null) return true;
    if (product.content === undefined || product.content === '' || product.content === null) return true;
    if (product.orderid === undefined || product.orderid === '' || product.orderid === null) return true;
}
function keyIsEmpty(key) {
    if (key === null) return true;
    if (key.key === undefined || key.key === '' || key.key === null) return true;
    if (key.backUrl === undefined || key.backUrl === '' || key.backUrl === null) return true;
}
function userIsEmpty(user) {
    if (user === null) return true;
    if (user.userId === undefined || user.userId === '' || user.userId === null) return true;
    if (user.gameId === undefined || user.gameId === '' || user.gameId === null) return true;
    if (user.token === undefined || user.token === '' || user.token === null) return true;
}
function productDel() {
    window.localStorage.removeItem('product')
}
/**
 * @return {string}
 */
function QueryString(name) {
    var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result === null || result.length < 1) {
        return "";
    }
    return result[1];
}