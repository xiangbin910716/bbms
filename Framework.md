# The framework of BBMS #
  1. Message
  1. BUS Manager
  1. Notifiable entry

# Features #
  1. Asynchronous event notification
  1. Distributed application support
  1. Allow separate business components to be combined into a reliable, flexible system.

# Workflow #
  1. BUS Manager start up and wait for client to register themselves.
  1. Client register to BUS to listener events(messages) it cares about.
  1. When message comes, BUS schedule the dispatch thread to send the message to appropriate node of listeners.
  1. Client unregister from BUS, and quit from the system.

# Developing a BBMS client #
  1. Find the BUS Manager(Message Service Provider)
  1. Register itself on the Manager
  1. Can send and receive messages from the BUS then.