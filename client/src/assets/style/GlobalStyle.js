import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
    @import url('https://spoqa.github.io/spoqa-han-sans/css/SpoqaHanSans-kr.css');
    @import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700&display=swap&subset=korean');
    @import url('https://fonts.googleapis.com/css?family=Lato:100,300,400,700,900&display=swap&subset=latin-ext');
    
    html {
        font-size: 13px;
    }
    input,button,img {
        vertical-align:middle;
    }
    html, body, div, span, object, iframe, 
    h1, h2, h3, h4, h5, h6, p, blockquote, pre,
    abbr, address, cite, code,
    del, dfn, em, img, ins, kbd, q, samp,
    small, strong, sub, sup, var,
    b, i, a,
    dl, dt, dd, ol, ul, li,
    fieldset, form, label, legend,
    table, caption, tbody, tfoot, thead, tr, th, td,
    article, aside, canvas, details, figcaption, figure, 
    footer, header, hgroup, menu, nav, section, summary,
    time, mark, audio, video {
        margin:0;
        padding:0;
        border:0;
        outline:0;
        text-decoration:none;
    }
    body {
        min-width:320px;
        line-height:1;
    }
    article,aside,details,figcaption,figure,
    footer,header,hgroup,menu,nav,section {
        display:block;
    }
    blockquote, q {
        quotes:none;
    }
    blockquote:before, blockquote:after,
    q:before, q:after {
        content:'';
        content:none;
    }
    table {
        border-collapse:collapse;
        border-spacing:0;
    }
    body, input, textarea, select, button, table {
        font-family:'Noto Sans KR','Spoqa Han Sans', 'Sans-serif','Nanum Gothic','Malgun Gothic','Roboto',Dotum,'돋움',Gulim,Helvetica,sans-serif;
        -webkit-text-size-adjust:none;
    }
    ul, ol {
        list-style-type:none;
    }
    input, select {
        vertical-align:middle;
    }
    a {
        text-decoration:none;
        color:#454545;
    }
    article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section {
        display:block;
    }
`;

export default GlobalStyle;
