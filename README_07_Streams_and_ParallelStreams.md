## Streams
We can use streams in both functional and declarative style.
![image info](./imgs/Screenshot_20240731_155206.png "Design patterns Mind Map")
*Streams are not containers. Don't confuse it with List or more generally with a Collection.*  
It is a very advanced and fancy Iterator which does not contain any data.  
**Streams are lazy** and they are also:
- Declarative
- Concise and Readable
- Flexible
- Parallelize

![image info](./imgs/Screenshot_20240731_172641.png "Streams")

### Streams Pipeline
A stream pipeline consists of:
- **Source** from which we generate the stream such as collection an array a generator function or an io channel.
- **Zero or more intermediate operations** these are operations that are applied on a stream, and they return another stream.
- **Terminal operation** produce a result or side effect from the stream.
  As we said a side effect is anything other than returning the result such as a print statement.

![image info](./imgs/Schermata_20240801_122547.png "Stream Pipeline")