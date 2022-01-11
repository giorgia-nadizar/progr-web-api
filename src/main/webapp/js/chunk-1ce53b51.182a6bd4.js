(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1ce53b51"],{"57b9":function(t,n,e){"use strict";e.r(n);var i=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("main",[e("h1",[t._v("I miei Consumers")]),e("b-button",{staticClass:"createNew",attrs:{variant:"outline-secondary",to:"/uploader/creaConsumer"}},[e("b-icon",{attrs:{icon:"person-plus-fill","shift-h":"-4"}}),t._v("Crea un nuovo Consumer ")],1),t.consumers.length?e("b-table",{attrs:{striped:"",outlined:"",responsive:"",stacked:"md",items:t.consumers,fields:t.fields,busy:t.loading,"head-variant":"dark"},scopedSlots:t._u([{key:"table-busy",fn:function(){return[e("div",{staticClass:"text-center my-2"},[e("b-spinner",{staticClass:"align-middle"}),e("strong",[t._v("Caricamento...")])],1)]},proxy:!0},{key:"cell(gestioneFiles)",fn:function(n){return[e("b-button",{staticClass:"mr-2",attrs:{size:"sm"},on:{click:function(e){n.toggleDetails(),t.getFiles(n.item.username,n.detailsShowing)}}},[t._v(" "+t._s(n.detailsShowing?"Nascondi":"Mostra")+" Files ")]),e("b-button",{attrs:{size:"sm",to:{name:"Carica File",params:{consumer:n.item.username}}}},[t._v("Carica file")])]}},{key:"cell(gestioneConsumer)",fn:function(n){return[e("b-button",{staticClass:"mr-2",attrs:{size:"sm",to:{name:"Modifica Consumer",params:{username:n.item.username}}}},[t._v("Modifica Dati")]),e("LoadingButton",{attrs:{size:"sm",variant:"secondary",submitting:n.item.deleting,text:"Elimina Consumer"},on:{submit:function(e){return t.askConfirm(n.item.username)}}})]}},{key:"row-details",fn:function(n){return[e("b-card",[n.item.files.length?e("b-table-simple",{staticClass:"table table-plain",attrs:{fields:t.fileFields,responsive:"",stacked:"md"}},[e("b-thead",[e("b-tr",[e("b-th",[t._v("Nome")]),e("b-th",[t._v("Hashtag")]),e("b-th",[t._v("Data Visualizzazione")]),e("b-th",[t._v("Indirizzo Ip Visualizzazione")]),e("b-th",[t._v("Elimina")])],1)],1),e("b-tbody",t._l(n.item.files,(function(i){return e("b-tr",{key:i.id},[e("b-td",[t._v(t._s(i.nome))]),e("b-td",[t._v(t._s(t._f("formatTag")(i.hashtag)))]),e("b-td",[t._v(" "+t._s(t._f("formatDate")(i.dataOraVisualizzazione))+" ")]),e("b-td",[t._v(t._s(i.ipVisualizzazione))]),e("b-td",[e("LoadingButton",{attrs:{size:"sm",variant:"secondary",submitting:i.deleting,icon:"trash"},on:{submit:function(e){return t.askConfirm(n.item.username,i.id)}}})],1)],1)})),1)],1):e("p",[t._v(" Non sono stati caricati file per questo consumer. ")])],1)]}}],null,!1,1118000840)}):t._e(),e("Confirm",{ref:"confirm",on:{confirm:t.confirmDelete}})],1)},s=[],a=(e("4de4"),e("7db0"),e("4160"),e("a15b"),e("159b"),e("5530")),r=e("c1c3"),o=e("c1df"),u=e("325d"),c=e("2f62"),l=e("9420"),f=e("8e8d"),d={components:{Confirm:l["a"],LoadingButton:f["a"]},data:function(){return{consumers:[],fields:["username","nomeCognome","email","gestioneFiles","gestioneConsumer"],fileFields:["nome","hashtag","dataOraVisualizzazione","ipVisualizzazione","Elimina"],icon:"trash",submitting:!1,loading:null}},created:function(){var t=this;this.loading=!0,u["a"].getUsers("consumer").then((function(n){n.data.forEach((function(t){t.files=[],t.deleting=!1})),t.consumers=n.data,t.loading=null})).catch((function(){t.error("Impossibile proseguire sulla pagina richiesta"),t.loading=null,t.logout()}))},filters:{formatDate:function(t){if(t)return o(String(t)).format("DD/MM/YYYY")},formatTag:function(t){if(t)return"#".concat(t.join(" #"))}},methods:Object(a["a"])(Object(a["a"])(Object(a["a"])({},Object(c["b"])("alert",["success","error"])),Object(c["b"])("account",["logout"])),{},{getFiles:function(t,n){var e=this;n||r["a"].getFilesForUploaders(t).then((function(n){n.data.forEach((function(t){t.deleting=!1})),e.consumers.find((function(n){return n.username===t})).files=n.data})).catch((function(){e.error("Impossibile caricare i file richiesti")}))},askConfirm:function(t,n){this.$refs.confirm.askConfirm(t,n)},confirmDelete:function(t,n){n?this.deleteFile(t,n):this.deleteConsumer(t)},deleteConsumer:function(t){var n=this;this.consumers.find((function(n){return n.username===t})).deleting=!0,u["a"].deleteUser(t).then((function(){n.consumers=n.consumers.filter((function(n){return n.username!==t})),n.success("Eliminazione consumer effettuata con successo")})).catch((function(){n.error("Eliminazione consumer fallita"),n.submitting=!1}))},deleteFile:function(t,n){var e=this;this.consumers.find((function(n){return n.username===t})).files.find((function(t){return t.id===n})).deleting=!0,r["a"].deleteFile(n).then((function(){e.consumers.find((function(n){return n.username===t})).files=e.consumers.find((function(n){return n.username===t})).files.filter((function(t){return t.id!==n})),e.success("Eliminazione file effettuata con successo")})).catch((function(){e.error("Eliminazione file fallita"),e.submitting=!1}))}})},m=d,b=e("2877"),h=Object(b["a"])(m,i,s,!1,null,null,null);n["default"]=h.exports},"7db0":function(t,n,e){"use strict";var i=e("23e7"),s=e("b727").find,a=e("44d2"),r=e("ae40"),o="find",u=!0,c=r(o);o in[]&&Array(1)[o]((function(){u=!1})),i({target:"Array",proto:!0,forced:u||!c},{find:function(t){return s(this,t,arguments.length>1?arguments[1]:void 0)}}),a(o)},"8e8d":function(t,n,e){"use strict";var i=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("b-overlay",{staticClass:"d-inline-block",attrs:{show:t.submitting,rounded:"",opacity:"0.6","spinner-small":"","spinner-variant":t.localVariant},on:{hidden:t.onHidden}},[e("b-button",{ref:"button",class:t.c,attrs:{size:t.localSize,disabled:t.disable,variant:t.localVariant},on:{click:function(n){return t.$emit("submit")}}},[t.icon?e("b-icon",{attrs:{icon:t.icon,"aria-hidden":"true"}}):t._e(),t._v(" "+t._s(t.text)+" ")],1)],1)},s=[],a={props:["submitting","icon","text","variant","size","disabled","c"],methods:{onHidden:function(){this.$refs.button.focus()}},computed:{localVariant:function(){return this.$props.variant?this.$props.variant:"primary"},localSize:function(){return this.$props.size?this.$props.size:null},disable:function(){return this.$props.submitting||this.$props.disabled}}},r=a,o=e("2877"),u=Object(o["a"])(r,i,s,!1,null,null,null);n["a"]=u.exports},9420:function(t,n,e){"use strict";var i=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("b-overlay",{attrs:{show:t.busy,"no-wrap":""},on:{shown:t.onShown},scopedSlots:t._u([{key:"overlay",fn:function(){return[e("div",{ref:"dialog",staticClass:"text-center p-3",attrs:{tabindex:"-1",role:"dialog","aria-modal":"false","aria-labelledby":"form-confirm-label"}},[e("p",[e("strong",{attrs:{id:"form-confirm-label"}},[t._v("Confermare l'eliminazione?")])]),e("div",{staticClass:"d-flex justify-content-center"},[e("b-button",{staticClass:"mr-3",attrs:{variant:"outline-danger"},on:{click:t.onCancel}},[t._v(" Annulla ")]),e("b-button",{attrs:{variant:"outline-success"},on:{click:t.onOK}},[t._v("Conferma")])],1)])]},proxy:!0}])})},s=[],a={data:function(){return{busy:!1,username:null,id:null}},methods:{askConfirm:function(t,n){this.busy=!0,this.username=t,this.id=n},onShown:function(){this.$refs.dialog.focus()},onCancel:function(){this.busy=!1},onOK:function(){this.busy=!1,this.$emit("confirm",this.username,this.id)}}},r=a,o=e("2877"),u=Object(o["a"])(r,i,s,!1,null,null,null);n["a"]=u.exports},a15b:function(t,n,e){"use strict";var i=e("23e7"),s=e("44ad"),a=e("fc6a"),r=e("a640"),o=[].join,u=s!=Object,c=r("join",",");i({target:"Array",proto:!0,forced:u||!c},{join:function(t){return o.call(a(this),void 0===t?",":t)}})}}]);
//# sourceMappingURL=chunk-1ce53b51.182a6bd4.js.map