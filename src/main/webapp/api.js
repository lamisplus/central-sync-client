export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'

// export const url =  'http://localhost:9090/api/v1/';
// export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNzAyOTk1MjExfQ.bfvhBCRuKr2GZW6fgbdez9uL-zjNJcYCq7CxLrdFCmddi4-VceZrgYpVBHijulwfpgD73nTxsVoL4tZp9saUvA'