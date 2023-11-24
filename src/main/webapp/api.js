export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'

// export const url =  'http://localhost:8789/api/v1/';
// export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNzAwNzY1NjU0fQ.BWFtG4knblWFfti9qMsgdIPEezj2XG0B3pINi_rHmyYdVeGgBx8qk_cdrt1vZpx66FjYpuHyf5bn79FV3B7FhQ'