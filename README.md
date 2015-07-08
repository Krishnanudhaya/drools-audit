Drools Audit
============

This component is providing auditing capabilities for Drools. This is currently implemented using the Listener KIE interfaces provided for working with Drools. 

## Rule Runtime Events ##

The runtime events currently being processed are centered around objects (inserted, updated, and deleted).

## Agenda Events ##

The agenda events currently being processed are centered around rule matches (created, cancelled, before/after fired), agenda groups (popped and pushed), and ruleflow groups (before/after activation, before/after deactiviation).
