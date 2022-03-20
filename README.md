#zipcode Project

This is a small test project for a Service 
that read the csv form:
https://launix.de/launix/launix-gibt-plz-datenbank-frei/

## Goal of this Service 
returning the Geocoordinates for a given German postal codes.

```
wget --header="Content-Type: text/json" http://localhost:8080/zipcode/20095
```
For getting this Response
```json
{
  "lat":53.5541579534295,
  "lon":10.0011036114924,
  "state":"Hamburg",
  "zipCode":20095
}
```
