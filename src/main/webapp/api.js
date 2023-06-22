export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'

// export const url =  'http://localhost:8787/api/v1/';
// export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNjg3NDI5Mzk2fQ.6Y8LlCekmf0EitC_iSwMttQcBwgH1EED_D-2aIu3HLw8T8wgYwIxxzWcv_Vt1a2bv_TuV4OAJoy7WETm93fmzA'