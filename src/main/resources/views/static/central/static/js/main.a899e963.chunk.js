(this.webpackJsonpLamisPlus=this.webpackJsonpLamisPlus||[]).push([[0],{550:function(e,t,a){},551:function(e,t,a){},652:function(e,t,a){"use strict";a.r(t);var r=a(0),c=a.n(r),i=a(21),o=a.n(i),l=a(47),s=a(55),n=(a(157),a(550),a(551),a(552),a(464)),d=a(704),j=a(722),b=a(703),u=a(131),O=a(369),f=a(243),h=a.n(f),g=a(502),p=a.n(g);var x=a(102),m=a.n(x),y=a(41),v=a.n(y);const w=new URLSearchParams(window.location.search).get("jwt"),S="/api/v1/";var R=a(4);var C=e=>{let{percentage:t}=e;return Object(R.jsx)("div",{className:"progress",children:Object(R.jsxs)("div",{className:"progress-bar progress-bar-striped bg-success",role:"progressbar",style:{width:"".concat(t,"%"),height:"80px"},children:[t,"%"]})})},N=a(305),A=a.n(N),k=a(105),B=a.n(k),P=a(115),z=a.n(P),F=a(106),I=a.n(F),T=a(113),L=a.n(T),_=a(76),D=a.n(_),M=a(75),U=a.n(M),E=a(107),K=a.n(E),W=a(108),H=a.n(W),V=a(110),q=a.n(V),G=a(111),J=a.n(G),Y=a(112),Q=a.n(Y),X=a(116),Z=a.n(X),$=a(109),ee=a.n($),te=a(114),ae=a.n(te),re=a(117),ce=a.n(re),ie=a(465),oe=(a(371),a(372),a(726)),le=a(708),se=a(709),ne=a(710),de=a(711),je=a(712),be=a(713),ue=a(714),Oe=a(715),fe=a(716),he=a(717),ge=a(183),pe=a.n(ge),xe=a(517),me=a.n(xe),ye=a(171),ve=a.n(ye),we=a(288),Se=a.n(we),Re=a(506),Ce=a.n(Re),Ne=(a(373),a(721)),Ae=a(723),ke=a(720);a(494);const Be={Add:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(B.a,{...e,ref:t}))),Check:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(I.a,{...e,ref:t}))),Clear:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),Delete:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(K.a,{...e,ref:t}))),DetailPanel:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(D.a,{...e,ref:t}))),Edit:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(H.a,{...e,ref:t}))),Export:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ee.a,{...e,ref:t}))),Filter:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(q.a,{...e,ref:t}))),FirstPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(J.a,{...e,ref:t}))),LastPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Q.a,{...e,ref:t}))),NextPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(D.a,{...e,ref:t}))),PreviousPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),ResetSearch:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),Search:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ae.a,{...e,ref:t}))),SortArrow:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(z.a,{...e,ref:t}))),ThirdStateCheck:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Z.a,{...e,ref:t}))),ViewColumn:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ce.a,{...e,ref:t})))},Pe=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"}})));var ze=e=>{Pe();const t=null===e.errorLogsToDisplay?[]:e.errorLogsToDisplay;return Object(R.jsx)("div",{children:Object(R.jsx)(oe.a,{isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{toggle:e.toggleModal,children:"Logs "}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(m.a,{icons:Be,title:"JSON Files Errors ",columns:[{title:"Name",field:"name",filtering:!1},{title:"Error",field:"error",filtering:!1},{title:"Others",field:"others",filtering:!1}],data:t.map((e=>({name:e.name,error:e.message,others:e.others}))),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!0,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})})]})})})},Fe=a(718);const Ie=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"},header:{fontWeight:600}})));var Te=e=>{const[t,a]=Object(r.useState)(null),c=Ie(),[i,o]=Object(r.useState)(!1),[l,n]=Object(r.useState)({}),[d,j]=Object(r.useState)(!1),[b,u]=Object(r.useState)([]),[O,f]=Object(r.useState)({});console.log(t),console.log(l),Object(r.useEffect)((()=>{e.rowObj&&(a(e.rowObj),n({...l,syncHistoryUuid:e.rowObj.id,syncHistoryTrackerUuid:e.rowObj.uuid,facilityId:e.rowObj.organisationUnitId}))}),[e.rowObj]),Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"sync/remote-urls"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{u(Object.entries(e.data).map((e=>{let[t,a]=e;return{label:a.url,value:a.id}})))})).catch((e=>{}))}()}),[]);const h=e=>{console.log(l),n({...l,[e.target.name]:e.target.value})};return Object(R.jsx)("div",{children:Object(R.jsx)(oe.a,{isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{className:c.header,toggle:e.toggleModal,children:"SEND TO SERVER "}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(de.a,{children:Object(R.jsxs)(je.a,{children:[Object(R.jsxs)(be.a,{children:[Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(Oe.a,{children:[Object(R.jsx)(fe.a,{children:"Username "}),Object(R.jsx)(he.a,{type:"text",name:"username",id:"username",value:l.username,onChange:h,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==O.username?Object(R.jsx)("span",{className:c.error,children:O.username}):""]})}),Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(Oe.a,{children:[Object(R.jsx)(fe.a,{children:"Password "}),Object(R.jsx)(he.a,{type:"password",name:"password",id:"password",value:l.password,onChange:h,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==O.password?Object(R.jsx)("span",{className:c.error,children:O.password}):""]})})]}),d?Object(R.jsx)(Fe.a,{}):"",Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:t=>{t.preventDefault(),(()=>{let e={...O};return e.username=l.username?"":"Username is required",e.password=l.password?"":"Password is required",f({...e}),Object.values(e).every((e=>""===e))})()&&(j(!0),v.a.post("".concat(S,"export/file/data"),l,{headers:{Authorization:"Bearer ".concat(w)}}).then((t=>{j(!1),s.b.success("Token Generated Successful"),e.toggleModal()})).catch((t=>{j(!1),s.b.error("Something went wrong"),e.toggleModal()})))},children:Object(R.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Send To Server"})})]})})})]})})})},Le=a(719);const _e=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},header:{fontWeight:600}})));var De=e=>{const t=_e();return Object(R.jsx)("div",{children:Object(R.jsx)(oe.a,{isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{className:t.header,toggle:e.toggleModal,children:"GENERATE KEY"}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(de.a,{children:Object(R.jsxs)(je.a,{children:[Object(R.jsxs)(be.a,{children:[Object(R.jsxs)(ue.a,{md:12,children:[Object(R.jsx)(Le.a,{color:"primary",children:Object(R.jsxs)("p",{style:{marginTop:".7rem"},children:["Info : \xa0\xa0\xa0",Object(R.jsx)("span",{style:{fontWeight:"bolder"},children:"Kindly copy the generated key for upload"}),"\xa0\xa0\xa0\xa0\xa0\xa0"]})}),Object(R.jsx)(u.a,{marginTop:2,children:e.genKey})]}),Object(R.jsx)(ue.a,{md:6})]}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{variant:"contained",color:"default",onClick:e.toggleModal,className:t.button,startIcon:Object(R.jsx)(pe.a,{}),children:"Cancel"})]})})})]})})})},Me=a(521);const Ue={Add:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(B.a,{...e,ref:t}))),Check:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(I.a,{...e,ref:t}))),Clear:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),Delete:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(K.a,{...e,ref:t}))),DetailPanel:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(D.a,{...e,ref:t}))),Edit:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(H.a,{...e,ref:t}))),Export:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ee.a,{...e,ref:t}))),Filter:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(q.a,{...e,ref:t}))),FirstPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(J.a,{...e,ref:t}))),LastPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Q.a,{...e,ref:t}))),NextPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(D.a,{...e,ref:t}))),PreviousPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),ResetSearch:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),Search:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ae.a,{...e,ref:t}))),SortArrow:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(z.a,{...e,ref:t}))),ThirdStateCheck:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Z.a,{...e,ref:t}))),ViewColumn:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ce.a,{...e,ref:t})))},Ee=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}})));var Ke=e=>{const t=Ee(),[a,c]=Object(r.useState)([]),[i,o]=Object(r.useState)([]),[l,n]=Object(r.useState)(!1),[d,j]=Object(r.useState)(!1),[b,u]=Object(r.useState)(""),[O,f]=Object(r.useState)(!1),[g,p]=Object(r.useState)(!1),[x,y]=Object(r.useState)([]),N=()=>n(!l),[k,B]=Object(r.useState)(!1),[P,z]=Object(r.useState)({facilityId:"",startDate:"",endDate:"",all:!1}),[F,I]=Object(r.useState)(!1),[T,L]=Object(r.useState)({}),[_,D]=Object(r.useState)(0),[M,U]=Object(r.useState)(!1),[E,K]=Object(r.useState)([]),[W,H]=Object(r.useState)(),[V,q]=Object(r.useState)(null);Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"account"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{o(Object.entries(e.data.applicationUserOrganisationUnits).map((e=>{let[t,a]=e;return{label:a.organisationUnitName,value:a.organisationUnitId}})))})).catch((e=>{}))}(),G()}),[]);async function G(){v.a.get("".concat(S,"export/sync-histories"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{c(e.data)})).catch((e=>{}))}return Object(R.jsxs)("div",{children:[!M&&Object(R.jsxs)(R.Fragment,{children:[Object(R.jsx)(ie.a,{variant:"contained",style:{backgroundColor:"#014d88"},className:" float-right mr-1",onClick:()=>{n(!l)},children:Object(R.jsx)("span",{style:{textTransform:"capitalize",color:"#fff"},children:"Generate JSON Files "})}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),Object(R.jsx)(m.a,{icons:Ue,title:"Generated JSON Files List ",columns:[{title:"Facility Name",field:"facilityName"},{title:"File Name ",field:"tableName",filtering:!1},{title:"Upload Size ",field:"uploadSize",filtering:!1},{title:"Upload Percentage ",field:"uploadPercentage",filtering:!1},{title:"Date Generated ",field:"date",filtering:!1},{title:"Status",field:"status",filtering:!1},{title:"Action",field:"actions",filtering:!1}],data:a.map((e=>{return{facilityName:e.facilityName,tableName:e.tableName,uploadSize:e.uploadSize,uploadPercentage:Object(R.jsx)("div",{children:Object(R.jsx)(Me.a,{now:e.percentageSynced,variant:(t=e.percentageSynced,t<=20?"danger":t>20&&t<=69?"warning":t>=70&&t<=99?"info":"success"),label:"".concat(e.percentageSynced,"%")})}),date:h()(e.dateLastSync).format("LLLL"),status:null===e.errorLog?0===e.processed?"Processing":"Completed":"Error",actions:Object(R.jsx)("div",{children:Object(R.jsx)(Ne.a.Menu,{position:"right",children:Object(R.jsx)(Ne.a.Item,{children:Object(R.jsx)(Ae.a,{style:{backgroundColor:"rgb(153,46,98)"},primary:!0,children:Object(R.jsx)(ke.a,{item:!0,text:"Action",children:Object(R.jsxs)(ke.a.Menu,{style:{marginTop:"10px"},children:[Object(R.jsxs)(ke.a.Item,{onClick:()=>{return t=e.tableName,void v.a.get("".concat(S,"export/download/").concat(t),{headers:{Authorization:"Bearer ".concat(w)},responseType:"blob"}).then((e=>{const a=e.data;let r=new Blob([a],{type:"application/octet-stream"});Ce.a.saveAs(r,"".concat(t))})).catch((e=>{}));var t},children:[Object(R.jsx)(me.a,{color:"primary"})," Download File"]}),Object(R.jsxs)(ke.a.Item,{onClick:()=>(e=>{f(!O),q(e)})(e),children:[Object(R.jsx)(A.a,{color:"primary"})," Send To Server"]}),Object(R.jsxs)(ke.a.Item,{onClick:()=>(e=>{u(e.genKey),j(!d)})(e),children:[Object(R.jsx)(ve.a,{color:"primary"}),"View Generate Key"]}),Object(R.jsxs)(ke.a.Item,{onClick:()=>(e=>{y(e.errorLog),p(!g)})(e),children:[Object(R.jsx)(ve.a,{color:"primary"}),"View Logs"]})]})})})})})})};var t})),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!0,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})]}),Object(R.jsx)(oe.a,{isOpen:l,toggle:N,className:e.className,size:"lg",backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{toggle:N,children:"Generate JSON Files"}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(de.a,{children:Object(R.jsxs)(je.a,{children:[Object(R.jsxs)(be.a,{children:[Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(Oe.a,{children:[Object(R.jsx)(fe.a,{children:"Facility *"}),Object(R.jsxs)(he.a,{type:"select",name:"facilityId",id:"facilityId",onChange:e=>{z({...P,[e.target.name]:e.target.value})},style:{border:"1px solid #014D88",borderRadius:"0.2rem"},vaulue:P.facilityId,children:[Object(R.jsx)("option",{children:" "}),i.map((e=>{let{label:t,value:a}=e;return Object(R.jsx)("option",{value:a,children:t},a)}))]}),""!==T.facilityId?Object(R.jsx)("span",{className:t.error,children:T.facilityId}):""]})}),Object(R.jsxs)("div",{className:"form-check custom-checkbox ml-3 ",children:[Object(R.jsx)("input",{type:"checkbox",className:"form-check-input",name:"all",id:"all",onChange:e=>{e.target.checked?z({...P,all:e.target.checked}):z({...P,all:!1})},checked:P.all}),Object(R.jsx)("label",{className:"form-check-label",htmlFor:"all",children:"Recent Update ?"})]})]}),Object(R.jsx)("br",{}),Object(R.jsx)("b",{children:!0===P.all?"Only the updated records will be pushed":"You are pushing record from initial"}),Object(R.jsx)("br",{}),F?Object(R.jsx)(C,{percentage:_}):"",Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{type:"submit",variant:"contained",color:"primary",className:t.button,style:{backgroundColor:"#014d88",fontWeight:"bolder"},startIcon:Object(R.jsx)(Se.a,{}),onClick:async e=>{if(e.preventDefault(),I(!0),(()=>{let e={...T};return e.facilityId=P.facilityId?"":"Facility is required",L({...e}),Object.values(e).every((e=>""===e))})())try{await v.a.get("".concat(S,"export/all?facilityId=").concat(P.facilityId,"&current=").concat(P.all),{headers:{Authorization:"Bearer ".concat(w)},onUploadProgress:e=>{D(parseInt(Math.round(100*e.loaded/e.total))),setTimeout((()=>D(0)),1e4)}});s.b.success("JSON Extraction was successful!"),N(),G(),I(!1)}catch(t){I(!1)}else s.b.error("Please select facility")},children:F?Object(R.jsx)("span",{style:{textTransform:"capitalize"},children:"Generating Please Wait..."}):Object(R.jsx)("span",{style:{textTransform:"capitalize"},children:"Generate"})}),Object(R.jsx)(ie.a,{variant:"contained",color:"default",onClick:N,className:t.button,style:{backgroundColor:"#992E62"},startIcon:Object(R.jsx)(pe.a,{}),children:Object(R.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"cancel"})})]})})})]})}),Object(R.jsx)(oe.a,{isOpen:k,toggle:()=>B(!k),backdrop:!1,fade:!0,style:{marginTop:"250px"},size:"lg",children:Object(R.jsx)(ne.a,{children:Object(R.jsx)("h1",{children:"Uploading File To Server. Please wait..."})})}),Object(R.jsx)(Te,{toggleModal:()=>f(!O),showModal:O,rowObj:V}),Object(R.jsx)(ze,{toggleModal:()=>p(!g),showModal:g,errorLogsToDisplay:x}),Object(R.jsx)(De,{toggleModal:()=>j(!d),showModal:d,genKey:b})]})};const We=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"},header:{fontWeight:600}})));var He=e=>{const t=We(),[a,c]=Object(r.useState)(!1),[i,o]=Object(r.useState)({file:""}),[l,n]=Object(r.useState)(!1),[d,j]=Object(r.useState)({}),[b,u]=Object(r.useState)(null),[O,f]=Object(r.useState)(null);return Object(R.jsx)("div",{children:Object(R.jsx)(oe.a,{isOpen:e.showModal,toggle:e.toggleModal,className:e.className,size:"lg",backdrop:"static",children:Object(R.jsxs)(le.a,{children:[Object(R.jsx)(se.a,{className:t.header,toggle:e.toggleModal,children:"UPLOAD CONFIG FILE"}),Object(R.jsx)(ne.a,{children:Object(R.jsx)(de.a,{children:Object(R.jsxs)(je.a,{children:[Object(R.jsx)(be.a,{children:Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(Oe.a,{children:[Object(R.jsx)(fe.a,{children:"Config File"}),Object(R.jsx)(he.a,{type:"file",name:"file",id:"file",accept:".json",onChange:e=>(t=>{const a=new FileReader;a.onloadend=()=>{try{u(JSON.parse(a.result)),f(a.result)}catch(e){u("**Not valid JSON file!**")}},void 0!==t&&a.readAsText(t)})(e.target.files[0]),style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==d.file?Object(R.jsx)("span",{className:t.error,children:d.file}):""]})})}),l?Object(R.jsx)(Fe.a,{}):"",Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:t=>{t.preventDefault(),n(!0),v.a.post("".concat(S,"sync/sync-config"),b,{headers:{Authorization:"Bearer ".concat(w)}}).then((t=>{n(!1),e.ServerUrl(),s.b.success("Config File Uploaded Successful"),e.toggleModal()})).catch((t=>{n(!1),s.b.error("Something went wrong"),e.toggleModal()}))},children:Object(R.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Upload Config File"})})]})})})]})})})};const Ve={Add:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(B.a,{...e,ref:t}))),Check:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(I.a,{...e,ref:t}))),Clear:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),Delete:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(K.a,{...e,ref:t}))),DetailPanel:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(D.a,{...e,ref:t}))),Edit:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(H.a,{...e,ref:t}))),Export:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ee.a,{...e,ref:t}))),Filter:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(q.a,{...e,ref:t}))),FirstPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(J.a,{...e,ref:t}))),LastPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Q.a,{...e,ref:t}))),NextPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(D.a,{...e,ref:t}))),PreviousPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),ResetSearch:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),Search:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ae.a,{...e,ref:t}))),SortArrow:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(z.a,{...e,ref:t}))),ThirdStateCheck:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Z.a,{...e,ref:t}))),ViewColumn:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ce.a,{...e,ref:t})))};var qe=e=>{const[t,a]=Object(r.useState)([]),[i,o]=Object(r.useState)(!1),[l,s]=c.a.useState(!1),[n,d]=Object(r.useState)(null),[j,b]=Object(r.useState)(!1);Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"sync/sync-config"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{a(e.data)})).catch((e=>{}))}()}),[]);return Object(R.jsxs)("div",{children:[Object(R.jsx)(ie.a,{variant:"contained",color:"primary",className:" float-right mr-1",style:{backgroundColor:"#014d88"},onClick:j?()=>{b(!1)}:()=>{s(!l)},children:Object(R.jsxs)("span",{style:{textTransform:"capitalize"},children:[j?"<< Back":"Upload Config File"," "]})}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),!j&&Object(R.jsx)(m.a,{icons:Ve,title:"Config Information ",columns:[{title:" File Name",field:"name"},{title:"Version",field:"version"},{title:"Release Date",field:"releaseDate",filtering:!1},{title:"Upload Date",field:"uploadDate",filtering:!1},{title:"Status",field:"status",filtering:!1},{title:"Action",field:"actions",filtering:!1}],data:t.map((e=>({name:e.name,version:e.version,releaseDate:e.releaseDate,uploadDate:e.uploadDate,status:!0===e.active?"Active":"previous",actions:Object(R.jsx)("div",{children:Object(R.jsx)(Ne.a.Menu,{position:"right",children:Object(R.jsx)(Ne.a.Item,{children:Object(R.jsx)(Ae.a,{style:{backgroundColor:"rgb(153,46,98)"},primary:!0,children:Object(R.jsx)(ke.a,{item:!0,text:"Action",children:Object(R.jsx)(ke.a.Menu,{style:{marginTop:"10px"},children:Object(R.jsxs)(ke.a.Item,{onClick:()=>(e=>{b(!0),d(e)})(e),children:[Object(R.jsx)(ve.a,{})," View "]})})})})})})})}))),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!1,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}}),j&&Object(R.jsx)(R.Fragment,{children:Object(R.jsx)(m.a,{icons:Ve,title:"Config Information - "+n.name,columns:[{title:" Module Name",field:"moduleName"},{title:"Version",field:"version"}],data:n.configModules.map((e=>({moduleName:e.moduleName,version:e.version}))),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!1,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})}),Object(R.jsx)(He,{toggleModal:()=>s(!l),showModal:l,ServerUrl:t})]})};var Ge=e=>{const[t,a]=Object(r.useState)({username:"",password:"",url:""}),[c,i]=Object(r.useState)(!1),[o,l]=Object(r.useState)([]);Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"sync/remote-urls")).then((e=>{console.log(e.data),l(Object.entries(e.data).map((e=>{let[t,a]=e;return{label:a.url,value:a.id}})))})).catch((e=>{}))}()}),[]);return Object(R.jsx)(qe,{})};const Je=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"}})));var Ye=e=>{const t=Je(),[a,c]=Object(r.useState)(null),[i,o]=Object(r.useState)({appKey:"",facilityId:"",id:"",serverUrl:""}),[l,n]=Object(r.useState)([]),[d,j]=Object(r.useState)(!1),[b,u]=Object(r.useState)({});Object(r.useEffect)((()=>{!async function(){v.a.get("".concat(S,"account"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{n(Object.entries(e.data.applicationUserOrganisationUnits).map((e=>{let[t,a]=e;return{label:a.organisationUnitName,value:a.organisationUnitId}})))})).catch((e=>{}))}(),async function(){v.a.get("".concat(S,"sync/app-key"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{c(e.data)})).catch((e=>{}))}()}),[e]);const O=e=>{o({...i,[e.target.name]:e.target.value})};return Object(R.jsxs)("div",{children:[Object(R.jsx)("h1",{children:"APP Key"}),Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{variant:"contained",color:"primary",className:" float-right mr-1",style:{backgroundColor:"#014d88"},onClick:()=>{e.showAppKeysListTable(!0)},children:Object(R.jsxs)("span",{style:{textTransform:"capitalize"},children:["<< Back"," "]})}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),null===e.keyObj?Object(R.jsxs)(R.Fragment,{children:[Object(R.jsxs)(be.a,{children:[Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(Oe.a,{children:[Object(R.jsx)(fe.a,{children:"Server URL *"}),Object(R.jsx)(he.a,{type:"text",name:"serverUrl",id:"serverUrl",onChange:O,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},vaulue:i.serverUrl}),""!==b.serverUrl?Object(R.jsx)("span",{className:t.error,children:b.serverUrl}):""]})}),Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(Oe.a,{children:[Object(R.jsx)(fe.a,{children:"Facility *"}),Object(R.jsxs)(he.a,{type:"select",name:"facilityId",id:"facilityId",onChange:O,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},vaulue:i.facilityId,children:[Object(R.jsx)("option",{children:" "}),l.map((e=>{let{label:t,value:a}=e;return Object(R.jsx)("option",{value:a,children:t},a)}))]}),""!==b.facilityId?Object(R.jsx)("span",{className:t.error,children:b.facilityId}):""]})}),Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)(Oe.a,{children:[Object(R.jsx)(fe.a,{children:"Key"}),Object(R.jsx)(he.a,{type:"text",name:"appKey",id:"appKey",value:i.appKey,onChange:O,style:{border:"1px solid #014D88",borderRadius:"0.2rem"},required:!0}),""!==b.appKey?Object(R.jsx)("span",{className:t.error,children:b.appKey}):""]})})]}),d?Object(R.jsx)(Fe.a,{}):"",Object(R.jsx)("br",{}),Object(R.jsx)(ie.a,{type:"submit",variant:"contained",style:{backgroundColor:"#014d88",fontWeight:"bolder"},onClick:t=>{t.preventDefault(),(()=>{let e={...b};return e.appKey=i.appKey?"":"Key is required",u({...e}),Object.values(e).every((e=>""===e))})()&&(j(!0),v.a.post("".concat(S,"sync/app-key"),i,{headers:{Authorization:"Bearer ".concat(w)}}).then((t=>{j(!1),s.b.success("APP KEY save successful"),e.showAppKeysListTable(!0),e.AppKeyHistory()})).catch((e=>{j(!1),s.b.error("Something went wrong")})))},children:Object(R.jsx)("span",{style:{textTransform:"capitalize ",color:"#fff"},children:"Update Key"})})]}):Object(R.jsx)(R.Fragment,{children:Object(R.jsx)(be.a,{children:Object(R.jsx)(ue.a,{md:12,children:Object(R.jsxs)("p",{children:["APP KEY : ",e.keyObj&&null!==e.keyObj?e.keyObj.appKey:""]})})})})]})};const Qe={Add:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(B.a,{...e,ref:t}))),Check:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(I.a,{...e,ref:t}))),Clear:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),Delete:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(K.a,{...e,ref:t}))),DetailPanel:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(D.a,{...e,ref:t}))),Edit:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(H.a,{...e,ref:t}))),Export:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ee.a,{...e,ref:t}))),Filter:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(q.a,{...e,ref:t}))),FirstPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(J.a,{...e,ref:t}))),LastPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Q.a,{...e,ref:t}))),NextPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(D.a,{...e,ref:t}))),PreviousPage:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(L.a,{...e,ref:t}))),ResetSearch:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(U.a,{...e,ref:t}))),Search:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ae.a,{...e,ref:t}))),SortArrow:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(z.a,{...e,ref:t}))),ThirdStateCheck:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(Z.a,{...e,ref:t}))),ViewColumn:Object(r.forwardRef)(((e,t)=>Object(R.jsx)(ce.a,{...e,ref:t})))},Xe=Object(n.a)((e=>({card:{margin:e.spacing(20),display:"flex",flexDirection:"column",alignItems:"center"},form:{width:"100%",marginTop:e.spacing(3)},submit:{margin:e.spacing(3,0,2)},cardBottom:{marginBottom:20},Select:{height:45,width:350},button:{margin:e.spacing(1)},root:{"& > *":{margin:e.spacing(1)}},input:{display:"none"},error:{color:"#f85032",fontSize:"11px"},success:{color:"#4BB543 ",fontSize:"11px"}})));var Ze=e=>{Xe();const[t,a]=Object(r.useState)([]),[c,i]=Object(r.useState)(!0),[o,l]=Object(r.useState)(null);async function s(){v.a.get("".concat(S,"sync/app-key"),{headers:{Authorization:"Bearer ".concat(w)}}).then((e=>{a(e.data)})).catch((e=>{}))}Object(r.useEffect)((()=>{s()}),[]);return Object(R.jsx)("div",{children:c?Object(R.jsxs)(R.Fragment,{children:[Object(R.jsx)(ie.a,{variant:"contained",style:{backgroundColor:"#014d88"},className:" float-right mr-1",onClick:()=>{i(!1)},disabled:t.length>0,children:Object(R.jsx)("span",{style:{textTransform:"capitalize",color:"#fff"},children:"Add App Key "})}),Object(R.jsx)("br",{}),Object(R.jsx)("br",{}),Object(R.jsx)(m.a,{icons:Qe,title:"APP Key List ",columns:[{title:"Facility Name",field:"facilityName"},{title:"Server Url",field:"serverUrl",filtering:!1},{title:"Action",field:"actions",filtering:!1}],data:t.map((e=>({facilityName:e.facilityName,serverUrl:e.serverUrl,actions:Object(R.jsx)("div",{children:Object(R.jsx)(Ne.a.Menu,{position:"right",children:Object(R.jsx)(Ne.a.Item,{children:Object(R.jsx)(Ae.a,{style:{backgroundColor:"rgb(153,46,98)"},primary:!0,children:Object(R.jsx)(ke.a,{item:!0,text:"Action",children:Object(R.jsx)(ke.a.Menu,{style:{marginTop:"10px"},children:Object(R.jsxs)(ke.a.Item,{onClick:()=>{return t=e,i(!1),void l(t);var t},children:[Object(R.jsx)(ve.a,{color:"primary"}),"View key"]})})})})})})})}))),options:{headerStyle:{backgroundColor:"#014d88",color:"#fff"},searchFieldStyle:{width:"200%",margingLeft:"250px"},filtering:!1,exportButton:!0,searchFieldAlignment:"left",pageSizeOptions:[10,20,100],pageSize:10,debounceInterval:400}})]}):Object(R.jsx)(R.Fragment,{children:Object(R.jsx)(Ye,{showAppKeysListTable:i,AppKeyHistory:s,keyObj:o})})})},$e=a(385),et=a.n($e),tt=a(518),at=a.n(tt);h.a.locale("en"),p()();const rt=Object(n.a)((e=>({header:{fontSize:"20px",fontWeight:"bold",padding:"5px",paddingBottom:"10px"},inforoot:{margin:"5px"},dropdown:{marginTop:"50px"},paper:{marginRight:e.spacing(2)},downmenu:{display:"flex"}})));function ct(e){const{children:t,value:a,index:r,...c}=e;return Object(R.jsx)(u.a,{component:"div",role:"tabpanel",hidden:a!==r,id:"scrollable-force-tabpanel-".concat(r),"aria-labelledby":"scrollable-force-tab-".concat(r),...c,children:a===r&&Object(R.jsx)(O.a,{p:5,children:t})})}function it(e){return{id:"scrollable-force-tab-".concat(e),"aria-controls":"scrollable-force-tabpanel-".concat(e)}}var ot=function(e){const t=rt(),[a,c]=Object(r.useState)(null),i=e.location&&e.location.state?e.location.state:" ",o=((e,t)=>{let a=t,r=new RegExp("[?&]"+e+"=([^&#]*)","i").exec(a);return r?r[1]:null})("tab",e.location&&e.location.search?e.location.search:""),l=null!==o?o:i;return Object(r.useEffect)((()=>{switch(l){case"database-sync":default:return c(0);case"setting":return c(1)}}),[o]),Object(R.jsxs)(R.Fragment,{children:[Object(R.jsx)("div",{className:"row page-titles mx-0",style:{marginTop:"0px",marginBottom:"-10px"},children:Object(R.jsx)("ol",{className:"breadcrumb",children:Object(R.jsx)("li",{className:"breadcrumb-item active",children:Object(R.jsx)("h4",{children:"Central Sync"})})})}),Object(R.jsx)("br",{}),Object(R.jsxs)("div",{className:t.root,children:[Object(R.jsx)(d.a,{position:"static",style:{backgroundColor:"#fff"},children:Object(R.jsxs)(j.a,{value:a,onChange:(e,t)=>{c(t)},variant:"scrollable",scrollButtons:"on",indicatorColor:"secondary",textColor:"primary","aria-label":"scrollable force tabs example",children:[Object(R.jsx)(b.a,{className:t.title,label:"Generate & Upload JSON Files",icon:Object(R.jsx)(at.a,{}),...it(0)}),Object(R.jsx)(b.a,{className:t.title,label:"APP Key Management",icon:Object(R.jsx)(et.a,{}),...it(2)}),Object(R.jsx)(b.a,{className:t.title,label:"Configuration  ",icon:Object(R.jsx)(et.a,{}),...it(1)})]})}),Object(R.jsx)(ct,{value:a,index:0,children:Object(R.jsx)(Ke,{})}),Object(R.jsx)(ct,{value:a,index:1,children:Object(R.jsx)(Ze,{})}),Object(R.jsx)(ct,{value:a,index:2,children:Object(R.jsx)(Ge,{})})]})]})};function lt(){return Object(R.jsx)(l.a,{children:Object(R.jsxs)("div",{children:[Object(R.jsx)(s.a,{}),Object(R.jsx)(l.d,{children:Object(R.jsx)(l.b,{path:"/",children:Object(R.jsx)(ot,{})})})]})})}var st=a(382);var nt=e=>{e&&e instanceof Function&&a.e(6).then(a.bind(null,835)).then((t=>{let{getCLS:a,getFID:r,getFCP:c,getLCP:i,getTTFB:o}=t;a(e),r(e),c(e),i(e),o(e)}))},dt=a(519);let jt="ltr";const bt=[{typography:"poppins",version:"light",layout:"vertical",headerBg:"color_1",navheaderBg:"color_1",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"full",direction:jt},{typography:"poppins",version:"light",layout:"vertical",primary:"color_5",headerBg:"color_5",navheaderBg:"color_1",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",direction:jt},{typography:"poppins",version:"light",layout:"vertical",navheaderBg:"color_11",headerBg:"color_1",sidebarBg:"color_11",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_11",direction:jt},{typography:"poppins",version:"dark",layout:"vertical",headerBg:"color_3",navheaderBg:"color_3",sidebarBg:"color_1",sidebarStyle:"full",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_1",direction:jt},{typography:"poppins",version:"light",layout:"vertical",navheaderBg:"color_15",headerBg:"color_1",sidebarStyle:"full",sidebarBg:"color_1",sidebarPosition:"fixed",headerPosition:"fixed",containerLayout:"wide",primary:"color_15",direction:jt},{typography:"poppins",version:"light",layout:"horizontal",navheaderBg:"color_1",headerBg:"color_1",sidebarBg:"color_9",sidebarStyle:"modern",sidebarPosition:"static",headerPosition:"fixed",containerLayout:"wide",primary:"color_9",direction:jt}],ut=Object(r.createContext)();var Ot=e=>{const[t,a]=Object(r.useState)({value:"full",label:"Full"}),[c,i]=Object(r.useState)({value:"fixed",label:"Fixed"}),[o,l]=Object(r.useState)({value:"fixed",label:"Fixed"}),[s,n]=Object(r.useState)({value:"vertical",label:"Vertical"}),[d,j]=Object(r.useState)({value:"ltr",label:"LTR"}),[b,u]=Object(r.useState)("color_1"),[O,f]=Object(r.useState)("color_1"),[h,g]=Object(r.useState)("color_1"),[p,x]=Object(r.useState)("color_1"),[m,y]=Object(r.useState)(!1),[v,w]=Object(r.useState)(!1),[S,C]=Object(r.useState)({value:"light",label:"Light"}),[N,A]=Object(r.useState)({value:"wide-boxed",label:"Wide Boxed"}),k=document.querySelector("body"),[B,P]=Object(r.useState)(0),[z,F]=Object(r.useState)(0),I=e=>{u(e),k.setAttribute("data-primary",e)},T=e=>{f(e),k.setAttribute("data-nav-headerbg",e)},L=e=>{g(e),k.setAttribute("data-headerbg",e)},_=e=>{x(e),k.setAttribute("data-sibebarbg",e)},D=e=>{i(e),k.setAttribute("data-sidebar-position",e.value)},M=e=>{j(e),k.setAttribute("direction",e.value);let t=document.querySelector("html");t.setAttribute("dir",e.value),t.className=e.value},U=e=>{"horizontal"===e.value&&"overlay"===t.value?(n(e),k.setAttribute("data-layout",e.value),a({value:"full",label:"Full"}),k.setAttribute("data-sidebar-style","full")):(n(e),k.setAttribute("data-layout",e.value))},E=e=>{"horizontal"===s.value&&"overlay"===e.value?alert("Sorry! Overlay is not possible in Horizontal layout."):(a(e),y("icon-hover"===e.value?"_i-hover":""),k.setAttribute("data-sidebar-style",e.value))},K=e=>{l(e),k.setAttribute("data-header-position",e.value)},W=e=>{k.setAttribute("data-theme-version",e.value),C(e)},H=e=>{A(e),k.setAttribute("data-container",e.value),"boxed"===e.value&&E({value:"overlay",label:"Overlay"})};return Object(r.useEffect)((()=>{const e=document.querySelector("body");e.setAttribute("data-typography","poppins"),e.setAttribute("data-theme-version","light"),e.setAttribute("data-layout","vertical"),e.setAttribute("data-primary","color_1"),e.setAttribute("data-nav-headerbg","color_1"),e.setAttribute("data-headerbg","color_1"),e.setAttribute("data-sidebar-style","overlay"),e.setAttribute("data-sibebarbg","color_1"),e.setAttribute("data-primary","color_1"),e.setAttribute("data-sidebar-position","fixed"),e.setAttribute("data-header-position","fixed"),e.setAttribute("data-container","wide"),e.setAttribute("direction","ltr");let t=()=>{P(window.innerWidth),F(window.innerHeight),window.innerWidth>=768&&window.innerWidth<1024?e.setAttribute("data-sidebar-style","mini"):window.innerWidth<=768?e.setAttribute("data-sidebar-style","overlay"):e.setAttribute("data-sidebar-style","full")};return t(),window.addEventListener("resize",t),()=>window.removeEventListener("resize",t)}),[]),Object(R.jsx)(ut.Provider,{value:{body:k,sideBarOption:[{value:"compact",label:"Compact"},{value:"full",label:"Full"},{value:"mini",label:"Mini"},{value:"modern",label:"Modern"},{value:"overlay",label:"Overlay"},{value:"icon-hover",label:"Icon-hover"}],layoutOption:[{value:"vertical",label:"Vertical"},{value:"horizontal",label:"Horizontal"}],backgroundOption:[{value:"light",label:"Light"},{value:"dark",label:"Dark"}],sidebarposition:c,headerPositions:[{value:"fixed",label:"Fixed"},{value:"static",label:"Static"}],containerPosition:[{value:"wide-boxed",label:"Wide Boxed"},{value:"boxed",label:"Boxed"},{value:"wide",label:"Wide"}],directionPosition:[{value:"ltr",label:"LTR"},{value:"rtl",label:"RTL"}],fontFamily:[{value:"poppins",label:"Poppins"},{value:"roboto",label:"Roboto"},{value:"cairo",label:"Cairo"},{value:"opensans",label:"Open Sans"},{value:"HelveticaNeue",label:"HelveticaNeue"}],primaryColor:b,navigationHader:O,windowWidth:B,windowHeight:z,changePrimaryColor:I,changeNavigationHader:T,changeSideBarStyle:E,sideBarStyle:t,changeSideBarPostion:D,sidebarpositions:[{value:"fixed",label:"Fixed"},{value:"static",label:"Static"}],changeHeaderPostion:K,headerposition:o,changeSideBarLayout:U,sidebarLayout:s,changeDirectionLayout:M,changeContainerPosition:H,direction:d,colors:["color_1","color_2","color_3","color_4","color_5","color_6","color_7","color_8","color_9","color_10","color_11","color_12","color_13","color_14","color_15"],haderColor:h,chnageHaderColor:L,chnageSidebarColor:_,sidebarColor:p,iconHover:m,menuToggle:v,openMenuToggle:()=>{"overly"===t.value?w(!0):w(!1)},changeBackground:W,background:S,containerPosition_:N,setDemoTheme:(e,t)=>{var a={},r=bt[e];k.setAttribute("data-typography",r.typography),a.value=r.version,W(a),a.value=r.layout,U(a),I(r.primary),T(r.navheaderBg),L(r.headerBg),a.value=r.sidebarStyle,E(a),_(r.sidebarBg),a.value=r.sidebarPosition,D(a),a.value=r.headerPosition,K(a),a.value=r.containerLayout,H(a),a.value=t,M(a)}},children:e.children})};o.a.render(Object(R.jsx)(c.a.StrictMode,{children:Object(R.jsx)(dt.a,{children:Object(R.jsx)(st.a,{basename:"/",children:Object(R.jsx)(Ot,{children:Object(R.jsx)(lt,{})})})})}),document.getElementById("root")),nt()}},[[652,1,2]]]);
//# sourceMappingURL=main.a899e963.chunk.js.map