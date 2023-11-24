export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'

// export const url =  'http://localhost:8789/api/v1/';
// export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNzAwODc0NTk4fQ.YFGSO17PgcCqSrlES9wevIwrLvncxnynj34pFQ1LoNVBK8X_sx95JhMf384qiywzhgLaB498Sg_em9ZqLiXC6A'