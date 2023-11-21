export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'

// export const url =  'http://localhost:8789/api/v1/';
// export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNzAwNTU3MjQ2fQ.QHxoBcydcDVI1G9oC0e1lMtQkxdZGyzwZmXfhgNBE0YSoALkvze4UR-wOTnDsWL7JE-WZ_sODkJvMOVly98VMA'