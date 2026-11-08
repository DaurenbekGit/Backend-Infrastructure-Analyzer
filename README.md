# Backend Infrastructure Analyzer

Software Design Patterns Assignment #6 · Java 25 · Visitor pattern

The analyzer applies two visitors to each backend component. One visitor itemizes monthly costs in US dollars. The other reports each disabled security control.

## Build and run

Use JDK 25 and Apache Maven 3.9.x. From the directory containing `pom.xml`, run these commands in order. The first compiles the project and runs its JUnit 5 tests. The second runs `Main` with the compiled classes. In IntelliJ IDEA, reload the Maven project after opening it, select JDK 25 as the project SDK, and run `kz.assignment.Main`.

```sh
mvn clean test
mvn exec:java
```

## Example report

| Component | Configuration | Monthly cost |
| --- | --- | ---: |
| API server | 2 instances × 2 cores per instance × $40 | $160.00 |
| PostgreSQL database | 100 GB of storage × $0.45 | $45.00 |
| Redis cache | 2 GB of memory × $10 | $20.00 |

The sample enables TLS, backups, database encryption and Redis private networking. Redis password authentication is disabled.

```text
Monthly cost report
API server: $160.00
PostgreSQL database: $45.00
Redis cache: $20.00
Total Monthly Cost: $225.00

Security report
- Redis cache has password authentication disabled.
Total Warnings: 1
```

## How double dispatch works

`Main` holds a `List<InfrastructureComponent>`. When it calls `component.accept(visitor)` on a Redis entry, Java dispatches to `RedisCache.accept`. Inside that method, the compiler knows that `this` is a `RedisCache`, so `visitor.visit(this)` selects the `visit(RedisCache)` overload. At runtime, the visitor object determines whether the cost or security implementation runs.

| Pattern role | Project type | Responsibility |
| --- | --- | --- |
| Element | `InfrastructureComponent` | Declares `accept(InfrastructureVisitor)`. |
| Concrete elements | `ApiServer`, `PostgresDatabase`, `RedisCache` | Hold immutable configuration and dispatch to the matching overload. |
| Visitor | `InfrastructureVisitor` | Declares one `visit` overload per concrete element. |
| Concrete visitors | `CostVisitor`, `SecurityVisitor` | Accumulate charges or warnings during one analysis. |
| Client | `Main` | Builds the sample inventory, applies both visitors and prints their reports. |

To add an operation such as a capacity report, implement `InfrastructureVisitor` and pass it to the existing elements. Adding a new component type requires adding its overload to the visitor interface and implementing that overload in each visitor.

## Validation and tests

Each numeric configuration field represents a provisioned quantity and must be a positive integer. Constructors reject zero and negative values with `IllegalArgumentException`. Each disabled security control produces one warning per affected inventory entry. Create fresh visitors for each independent analysis.

The tests under `src/test/java/kz/assignment` cover the three rates, exact cent arithmetic, large CPU totals, all five security controls, the sample totals, constructor validation and dispatch through the component interface.
