# Basic Packet
> ```
>  msg {
>     nknm : string
>     message : string
>     room : string
>         ...
>         ...
>  }
> ```

# Server Side
> ## Rooms[]
> ```
>  Room {
>     rid : string
>     numUsers : int
>     time : string // temp
>     start : position { } // Expected
>     finish : position { } // Expected
>         ...
>         ...
>  }
> ```

# Protocol
> ## Client to Server
>> ### new message
>>> Send the message to Server
>>> ```
>>>  msg {
>>>     nknm : string
>>>     message : string
>>>     rid : string
>>>  }
>>> ```
>>
>> ### show room
>>> Return Rooms[]
>>> ```
>>>  msg {
>>>     nknm : string
>>>     position : { } // Expected
>>>     start : string
>>>     finish : string
>>>     time : string
>>>  }
>>> ```
>>
>> ### make room
>>> Ceate a room and join in
>>> ```
>>>  msg {
>>>     nknm : string
>>>     position : { } // Expected
>>>     start : string
>>>     finish : string
>>>     time : string
>>>  }
>>> ```
>>
>> ### join room
>>> join in room
>>> ```
>>>  msg {
>>>     nknm : string
>>>     rid : string
>>>  }
>>> ```
>>
>> ### leave room
>>> leave the room
>>> ```
>>>  msg {
>>>     nknm : string
>>>     rid : string
>>>  }
>>> ```
>
> ## Server to Client
>> ### new message
>>> Broadcast the message to clients in the same room
>>> ```
>>>  msg {
>>>     nknm : string
>>>     message : string
>>>     rid : string
>>>  }
>>> ```
>>
>> ### show room
>>> Send room list
>>> ```
>>>  msg {
>>>     rooms : Rooms[]
>>>  }
>>> ```
>>
>> ### room id
>>> Send room id
>>> ```
>>>  msg {
>>>     rid : string
>>>  }
>>> ```

 - - -

Last Modified : 2018-04-25
