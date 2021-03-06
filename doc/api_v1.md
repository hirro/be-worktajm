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

## POST /person

Create a person.

API returns:
**200** on success with copy of created object WITH id.
**400** on error

#### example request

    $ curl -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/api/person/1 -X PUT \
      -F 'phone=6041234567'


## GET /person/{person id}

Read the person with the specified id.
If no id is specified, the currently logged in person will be retrieved.

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
       "email": "john.doe@example.com"
    }


## PUT /person

Update a person.
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

## DELETE /person/{id}

Deletes a person with the specified person id.

#### example request

    $ curl -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/api/person/1 -X DELETE \
      -F 'phone=6041234567'




# Time Entry

## GET /timeEntry/{time entry id}

Expects basic auth to get an existing customer. API will return **200**.

#### example request

    $ curl -k -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/api/timeEntry/1

#### response

    {
      "id": 1,
      "startTime": 1386358504000,
      "endTime": 1386365705000,
      "comment": null,
      "links": 
      [ 
        {
          "rel": "project",
          "href": "https://worktajm.arnellconsulting.dyndns.org:8080/api/project/1"
        },
        {
          "rel": "person",
          "href": "https://worktajm.arnellconsulting.dyndns.org:8080/api/person/1"
        }        
      ]
    }    

## POST /timeEntry

Creates a new time entry.

#### Example request

    $ curl -k -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/api/timeEntry/1

#### Example response

## PUT /timeEntry

Update a new time entry.

#### Example request

    $ curl -k -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/api/timeEntry/1

#### Example response
## GET /timeEntries

Gets all time entries for the logged in person.

#### Example response

    {
      "links": 
      [ 
        {
          "rel": "timeEntry",
          "href": "https://worktajm.arnellconsulting.dyndns.org:8080/api/timeEntry/1"
        },
        {
          "rel": "timeEntry",
          "href": "https://worktajm.arnellconsulting.dyndns.org:8080/api/timeEntry/2"
        },
        {
          "rel": "timeEntry",
          "href": "https://worktajm.arnellconsulting.dyndns.org:8080/api/timeEntry/3"
        }        
      ]
    }


# Status Report

## GET /status/

Returns the status of the asking user.

#### Example request

      $ curl -l -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/status

#### Example response

      {
        "last_day": {
          
        }
      }


#### Example response

# Email lookup

## GET /registration/isEmailUnique?email=a@b.cde

Returns true if email is available for registration.

Note to self, might need to restrict this.

#### Example request

      $ curl -l -u john.doe@example.com:password https://worktajm.arnellconsulting.dyndns.org:8080/registration/isEmailUniuqe?email=a@b.cde

#### Example response

      true


#### Example response

