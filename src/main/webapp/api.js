export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'

// export const url =  'http://localhost:9090/api/v1/';
// export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlY2V3c0FDRTUiLCJhdXRoIjoiU3VwZXIgQWRtaW4sVXNlciIsIm5hbWUiOiJFQ0VXUyBBQ0U1IiwiZXhwIjoxNzM0MzcyMjQ3fQ.C8dvpQxWvFyXOX2DjVeyrtUW0JJDwRUzk_Ior1qngBDTH6rv1K-qUmzR_Gkljmns-4eOfHV_Kli1I0kdowmQnA'