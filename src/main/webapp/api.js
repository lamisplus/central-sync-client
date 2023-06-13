export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'

// export const url =  'http://localhost:8787/api/v1/';
// export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNjg2NTgxMTc5fQ.XfuMVJIp9lhc35IXjuGFI7yObuXlWq6PL7A7SX6dKutAtAUVkJgXWkIzYPS88G8xwqj3reLe4xEToUHR9FAawA'