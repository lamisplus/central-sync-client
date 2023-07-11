export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'
// export const url =  'http://localhost:8787/api/v1/';
// export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNjg5MDk3MTk1fQ.WjGgzI48IZVGEzKO6yGEWIIAP7mtMwVLPMzq2T90ksZ-ib0TBZ1t4plynRsOU2AHOcu3NGMwylGYptCDOISjnw'