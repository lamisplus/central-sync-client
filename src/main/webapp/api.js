export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'

// export const url =  'http://localhost:8789/api/v1/';
// export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNzAxMDA3NjI0fQ.hWqk5mf3s0Sypvh9S63wror5Lck22mNkG9BTeN9XqdcV4zpTkMY9vzJ5lilYiTLjk90qdaKl_W4lzt_kBMSlNQ'