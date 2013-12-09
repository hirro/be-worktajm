---
title: WorkTajm API
brand: com.arnellconsulting.worktajm
version: 1.0.0
---

# WorkTajm API

### All API calls start with

<pre class="base">
https://worktajm.arnellconsulting.dyndns.org:8080/api
</pre>

### Path

For this documentation, we will assume every request begins with the above path.

### Format

All calls are returned in **JSON**.

### Status Codes

- **200** Successful GET and PUT.
- **201** Successful POST.
- **202** Successful Provision queued.
- **204** Successful DELETE
- **401** Unauthenticated.
- **409** Unsuccessful POST, PUT, or DELETE (Will return an errors object)



# Person

## GET /person/{person id}

Gets the person with the specified id.
API will return **200**.

#### example request

    $ curl -k -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/apiperson/1

#### response

    {
       "id": 1,
       "emailVerified": false,
       "activeTimeEntry": 3,
       "firstName": "John",
       "lastName": "Doe",
       "email": "john.doe@example.com",
       "password": "*******"
    }


## PUT /person

Create a person.
API returns:
**200** on success.
**400** on error

#### example request

    $ curl -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/api/person/1 -X PUT \
      -F 'phone=6041234567'

#### response

    {
       "id": 1,
       "emailVerified": false,
       "activeTimeEntry": 3,
       "firstName": "John",
       "lastName": "Doe",
       "email": "john.doe@example.com"
    }

# Time Entry

## GET /timeEntry/{time entry id}

Expects basic auth to get an existing customer. API will return **200**.

#### response

    $ curl -k -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/api/timeEntry/1

    {
       "id": 1,
       "startTime": 1386358504000,
       "endTime": 1386365705000,
       "comment": null,
       "project": {
          "id": 2,
          "name": "B",
          "description": null,
          "rate": 555,
          "new": false
       },
       "new": false
    }    

## 



