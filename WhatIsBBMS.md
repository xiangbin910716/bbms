# Introduction #

BBMS is a Bus Based Message Service, used to communication between applications, **_asynchronous_**.

Messages is actually a java object which contains source address, target address, message body, and message type. It use the java RMI API to deliver messages (event generally) over net.
So, a particular message package looks like this:

  * Source address
  * Target address
  * Message body (is a java-Object, may contain **_any_** content)
  * Message type (very important when notify the _listeners_ )

Message type used to identify a special **notifiable object** which messages it can received. And other type of messages just be passed.


# Bus Based #

BUS Based means all notifiable entry register themselves on the BUS, so they can post message (event) to the BUS, and got notification from the BUS. Who else mounted on the BUS, for a specail node, is unconcerned.

One thing need to know is that all node mounted on the BUS is Notifiable, so we can simply post messages to BUS, and BUS will do the dispatching and invoke the listener handler appropriately.